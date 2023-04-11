@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class MessageAnno {
    object MessageType {
        const val LOGIN = 0x01//登录
        const val JOIN_ROOM = 0x02//加入房间
        const val CALL = 0x03//呼叫某人
        const val REPLY = 0x04 //回复某人呼叫
        const val OFFER = 0x05 // p2p offer信息
        const val ANSWER = 0x06//p2p answer 信息
        const val LEAVE_ROOM = 0x07 //离开房间
        const val HANG_UP = 0x08 // 挂断

    }
}

object CallType {
    const val CALL = 0// 呼叫
    const val MEETING = 1 //会议
}