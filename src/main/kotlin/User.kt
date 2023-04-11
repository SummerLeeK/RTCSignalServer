import com.alibaba.fastjson.JSON
import org.java_websocket.WebSocket
import java.lang.ref.WeakReference
import java.util.*

class User(client: WebSocket, UserID: String) : Observer {
    private val mWeakSocket: WeakReference<WebSocket>
    val mUserId: String
    private var sdp: String? = null
    private var iceCandidate: String? = null

    init {
        mWeakSocket = WeakReference(client)
        mUserId = UserID
    }

    fun sendClientMsg(msg: SignalMessage) {
        System.out.println("sendClientMsg ${mUserId}")
        mWeakSocket.get()?.send(JSON.toJSONString(msg))
    }

    override fun update(o: Observable?, arg: Any?) {
        arg?.let {
            if (it is SignalMessage) {
                if (!it.includeSender && it.userId.equals(mUserId))
                    return
                sendClientMsg(it)
            }
        }
    }
}