import org.java_websocket.WebSocket
import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.nio.ByteBuffer

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
class ChatServer : WebSocketServer {
    constructor(port: Int) : super(InetSocketAddress(port))
    constructor(address: InetSocketAddress?) : super(address)
    constructor(port: Int, draft: Draft_6455) : super(InetSocketAddress(port), listOf<Draft>(draft))

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        conn.send("Welcome to the server!") //This method sends a message to the new client
        broadcast(
            "new connection: " + handshake
                .resourceDescriptor
        ) //This method sends a message to all clients connected
        println(
            conn.remoteSocketAddress.address.hostAddress + " entered the room!"
        )
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        broadcast("$conn has left the room!")
        println("$conn has left the room!")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        broadcast(message)
        println("$conn: $message")
    }

    override fun onMessage(conn: WebSocket, message: ByteBuffer) {
        broadcast(message.array())
        println("$conn: $message")
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        ex.printStackTrace()
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    override fun onStart() {
        println("Server started!")
        connectionLostTimeout = 0
        connectionLostTimeout = 100
    }
}