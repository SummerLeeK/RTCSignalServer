import org.java_websocket.WebSocket

class UserManager {
    private var allUsers = mutableMapOf<String, User>()//用户id 用户信息


    companion object {
        private var mInstance: UserManager? = null
            get() {
                if (field == null) {
                    field = UserManager()
                }
                return field
            }

        @JvmStatic
        fun get(): UserManager {
            return mInstance!!
        }
    }


    /**
     * 添加一个用户
     */
    fun addUser(userId: String, client: WebSocket) {
        val user = User(client, userId)
        allUsers.put(userId, user)
    }

    fun getUserInfo(userId: String): User? {
        return allUsers.get(userId)
    }

    fun removeUser(userid: String) {
        allUsers.remove(userid)
    }

}