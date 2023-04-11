import java.io.BufferedReader
import java.io.InputStreamReader

fun main(args: Array<String>) {
    println("Hello World!")
    SignalServer.start()
    val sysin = BufferedReader(InputStreamReader(System.`in`))
    while (true) {
        val `in` = sysin.readLine()
        if (`in` == "exit") {
            SignalServer.stop()
            break
        }
    }
}