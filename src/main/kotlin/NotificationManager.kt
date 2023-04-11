class NotificationManager {

    companion object {
        private var mInstance: NotificationManager? = null
            get() {
                if (field == null) {
                    field = NotificationManager()
                }
                return field
            }

        @JvmStatic
        fun get(): NotificationManager {
            return mInstance!!
        }
    }


    /**
     * 发送一条通知消息到群中
     */
    fun notificationToRoom(roomid: String, msg: SignalMessage) {
        if (RoomManager.get().isRoomExist(roomid)) {
            val room = RoomManager.get().getRoom(roomid)
            room?.observe(msg)
        }
    }

    /**
     * 发送一条消息给用户
     */
    fun notificationUser(userId:String,msg: SignalMessage){
        UserManager.get().getUserInfo(userId)?.let {
            it.sendClientMsg(msg)
        }
    }

}