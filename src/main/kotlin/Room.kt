import java.util.Observable
import java.util.Observer

class Room(room: String, creator: User) {
    val roomId: String
    val creator: User
    private var allUsers = mutableMapOf<String, User>()

    private var mRoomEventObserver: Observable

    init {
        roomId = room
        this.creator = creator
        mRoomEventObserver = Observable()
        addUser(creator)
    }

    fun addUser(people: User) {
        mRoomEventObserver.addObserver(people)
        allUsers.put(people.mUserId, people)
    }

    fun removeUser(userId: String) {
        val user = getUser(userId)
        mRoomEventObserver.deleteObserver(user)
        allUsers.remove(userId)
    }


    fun getUser(userId: String): User {
        return allUsers.get(userId)!!
    }

    fun getAllUsers(): List<User> {
        return allUsers.values.toList()
    }

    fun observe(msg:SignalMessage){
        mRoomEventObserver.notifyObservers(msg)
    }

}