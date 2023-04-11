import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONException
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress

object SignalServer {

    private var allClientWithUserid= mutableMapOf<WebSocket,String>()
    var socket: WebSocketServer? = null
    fun start() {
        socket = object : WebSocketServer(InetSocketAddress("0.0.0.0", 8888)) {
            override fun onOpen(p0: WebSocket?, p1: ClientHandshake?) {
                System.out.println("onOpen")
            }

            override fun onClose(p0: WebSocket?, p1: Int, p2: String?, p3: Boolean) {
                System.out.println("onClose")

                p0?.let {
                    handleClientDisconnect(it)
                }
            }

            override fun onMessage(client: WebSocket, msg: String?) {
                System.out.println("onMessage ${msg}")

                msg?.let {
                    try {
                        val signalMsg = JSON.parseObject(msg, SignalMessage::class.java)

                        when (signalMsg.type) {
                            MessageAnno.MessageType.LOGIN -> {
                                handleLogin(client,signalMsg)
                            }

                            MessageAnno.MessageType.JOIN_ROOM -> {
                                handleJoinRoom(client, signalMsg)
                            }

                            MessageAnno.MessageType.CALL -> {

                            }

                            MessageAnno.MessageType.OFFER -> {

                            }

                            MessageAnno.MessageType.ANSWER -> {

                            }

                            MessageAnno.MessageType.LEAVE_ROOM -> {
                                handleLeaveRoom(client)
                            }

                            MessageAnno.MessageType.HANG_UP -> {

                            }

                        }
                    } catch (e: JSONException) {

                    }

                }

            }

            override fun onError(client: WebSocket?, p1: Exception?) {
                System.out.println("onError ${p1?.toString()}")
                client?.let {
                    handleClientDisconnect(it)
                }
            }

            override fun onStart() {
                System.out.println("onStart")
            }
        }

        println("${socket?.address}\t${socket?.port}")
        socket?.start()
    }

    fun stop() {
        socket?.stop()
    }


    /**
     * 处理用户登录 保存用户信息
     */
    private fun handleLogin(client: WebSocket, msg: SignalMessage) {
        allClientWithUserid.put(client,msg.userId)
        UserManager.get().addUser(msg.userId, client)
        println("handleLogin\t${msg.userId} from \t${client.remoteSocketAddress}")
    }

    /**
     * 处理用户加入群逻辑
     */
    private fun handleJoinRoom(client: WebSocket, msg: SignalMessage) {
        if (RoomManager.get().isUserInRoom(msg.userId,msg.targetId)) {
            // 当前用户已经在群中
            println("handleJoinRoom\t${msg.userId} already in Room!  \t${client.remoteSocketAddress}")
            return
        }
        if (!RoomManager.get().isRoomExist(msg.targetId)) {
            //第一个进来 创建房间
            val userId = msg.userId
            val creator = User(client, userId)
            RoomManager.get().createRoom(msg.targetId,creator)
            RoomManager.get().joinRoom(msg.userId,msg.targetId)
        } else {
            //加入房间
            RoomManager.get().joinRoom(msg.userId,msg.targetId)
        }

    }

    private fun handleCall(){
    }

    private fun handleLeaveRoom(client: WebSocket) {
        handleClientDisconnect(client)
    }

    /**
     * 客户端断开连接 移除用户
     */
    private fun handleClientDisconnect(client: WebSocket) {
        if (allClientWithUserid.containsKey(client)) {//判断该client是否连接过
            val userId = allClientWithUserid.get(client)!!

            UserManager.get().removeUser(userId)

            if (RoomManager.get().isUserAlreadyInRoom(userId)){
                val roomId=RoomManager.get().getUserRoomId(userId)
                RoomManager.get().exitRoom(userId,roomId)
            }
            allClientWithUserid.remove(client)
        }
    }
}