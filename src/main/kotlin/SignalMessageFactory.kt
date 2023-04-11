object SignalMessageFactory {



    fun buildJoinRoom(roomId:String,userId:String) :SignalMessage{
        return SignalMessage(MessageAnno.MessageType.JOIN_ROOM,roomId,null,CallType.MEETING,userId)
    }
    fun buildLeaveRoom(roomId:String,userId:String) :SignalMessage{
        return SignalMessage(MessageAnno.MessageType.LEAVE_ROOM,roomId,null,CallType.MEETING,userId)
    }

    fun buildCall(senderUserId:String,remoteUserId: String,sdp:String):SignalMessage{
        return SignalMessage(MessageAnno.MessageType.CALL,remoteUserId,sdp,CallType.CALL,senderUserId)
    }

    fun buildReply(senderUserId:String,remoteUserId: String,sdp:String):SignalMessage{
        return SignalMessage(MessageAnno.MessageType.REPLY,remoteUserId,sdp,CallType.CALL,senderUserId)
    }

    fun buildHangUp(senderUserId:String,remoteUserId: String,sdp:String):SignalMessage{
        return SignalMessage(MessageAnno.MessageType.HANG_UP,remoteUserId,sdp,CallType.CALL,senderUserId)
    }
}