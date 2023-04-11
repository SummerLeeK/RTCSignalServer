class RoomManager {
    private var roomMap = mutableMapOf<String, Room>() // 房间id 房间
    private var allUserRoom = mutableMapOf<String, String>()//用户id 房间id

    companion object {
        private var mInstance: RoomManager? = null
            get() {
                if (field == null) {
                    field = RoomManager()
                }
                return field
            }

        @JvmStatic
        fun get(): RoomManager {
            return mInstance!!
        }
    }


    fun isRoomExist(roomId: String): Boolean {
        return roomMap.containsKey(roomId)
    }

    fun createRoom(roomId: String,creator:User){
        val room = Room(roomId, creator)
        roomMap.put(roomId,room)
    }

    fun getRoom(roomId:String):Room?{
        return roomMap.get(roomId)
    }


    fun joinRoom(userId: String, roomId: String) {
        allUserRoom.put(userId, roomId)
        //通知房间内其他成员 有新人加入房间
        val notification = SignalMessageFactory.buildJoinRoom(roomId, userId)
        NotificationManager.get().notificationToRoom(roomId,notification)
    }

    fun exitRoom(userId: String, roomId: String) {
        allUserRoom.remove(userId)
        val room=getRoom(roomId)
        room?.removeUser(userId)
        //通知房间内其他成员 有新人离开房间
        val notification = SignalMessageFactory.buildLeaveRoom(roomId, userId)
        NotificationManager.get().notificationToRoom(roomId,notification)
    }


    fun isUserInRoom(userId:String,roomId:String):Boolean{
        if (!allUserRoom.containsKey(userId)){
            return false
        }

        return (allUserRoom.get(userId).equals(roomId))
    }

    fun isUserAlreadyInRoom(userid:String):Boolean{
        return allUserRoom.containsKey(userid)
    }


    fun getUserRoomId(userid:String):String{
        return allUserRoom.get(userid)!!
    }
}