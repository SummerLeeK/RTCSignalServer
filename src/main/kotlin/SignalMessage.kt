/**
 * @param type 消息类型 加入房间/退出房间等
 * @param targetId 对方id 房间id/对方id
 * @param data 消息具体信息 如sdp
 * @param callType 1v1通话/1vn通话
 * @param userId 当前用户id
 * @param includeSender 通知消息是否包含当前userId
 */
data class SignalMessage(
    @MessageAnno val type: Int,
    val targetId: String,
    val data: String? = null,
    val callType: Int,
    val userId: String,
    @Transient var includeSender: Boolean = false
) {

}

