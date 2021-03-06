package com.dhbw.ceangal.usermodel

import com.dhbw.ceangal.error.UserAlreadyExistsException
import com.dhbw.ceangal.error.UserNotFoundException
import com.dhbw.ceangal.error.WrongPasswordException
import com.dhbw.ceangal.friend.Friend
import com.dhbw.ceangal.friend.FriendRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * This class executes user commands. It functions for creating, editing and deleting a user.
 */
@Service
class UserService:  UserInterface {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userSessionRepository: UserSessionRepository
    @Autowired
    lateinit var friendRepository: FriendRepository

    /**
     * This function adds the user to the Repository
     *
     * @param userProfile the user to be created
     * @return the saved UserProfile
     */
    override fun createUser(userProfile: UserProfile): UserProfile {
        val userList = userRepository.findAll()
        userList.forEach {

            if(it.email.equals(userProfile.email) && it.username.equals(userProfile.username))
            {
                throw UserAlreadyExistsException("Email and Username already exists")
            }
            if(it.username.equals(userProfile.username))
            {
                throw UserAlreadyExistsException("Username already exists")
            }
            if(it.email.equals(userProfile.email))
            {
                throw UserAlreadyExistsException("Email already exists")
            }
        }
        return userRepository.save(userProfile)
    }

    /**
     * This function edits the user in the Repository
     *
     * @param userProfile the user to be created
     * @param id the id of the user whose data should be changed
     * @return the saved UserProfile
     */
    override fun editUser(userProfile: UserProfile, id: String): UserProfile {
        val optionalUserSession = userSessionRepository.findById(id)
        if (optionalUserSession.isEmpty) {
            throw UserNotFoundException()
        }
        val userSession = optionalUserSession.get()
        val userId = userSession.userId
        val optionalUser = userRepository.findById(userId)

        if (optionalUser.isEmpty) {
            throw UserNotFoundException("User wasn't found in repo.")
        }
        val user = optionalUser.get()

        val userList = userRepository.findAll()
        userList.forEach {
            if (user.id == it.id) {}
            else if (it.email.equals(userProfile.email) && it.username.equals(userProfile.username)) {
                throw UserAlreadyExistsException("Email and Username already exists")
            }
            else if (it.username.equals(userProfile.username)) {
                throw UserAlreadyExistsException("Username already exists")
            }
            else if (it.email.equals(userProfile.email)) {
                throw UserAlreadyExistsException("Email already exists")
            }
        }
        if(userProfile.username != "")
        {
            user.username = userProfile.username
        }
        if(userProfile.email != "")
        {
            user.email = userProfile.email
        }
        if(userProfile.description != "")
        {
            user.description = userProfile.description
        }
        return userRepository.save(user)
    }
    /**
     * This function deletes the user from the Repository
     *
     * @param id the id of the user who should be deleted
     */
    override fun deleteUser(id: String) {
        val optionalUserSession = userSessionRepository.findById(id)
        if (optionalUserSession.isEmpty) {
            throw UserNotFoundException()
        }
        val userSession = optionalUserSession.get()
        val userId = userSession.userId

        if (userRepository.findById(userId).isEmpty) {
            throw UserNotFoundException()
        }

        userRepository.deleteById(userId)
    }
    override fun login(userProfile: UserProfile): String {
        val userList = userRepository.findAll()
        userList.forEach {
            if(it.email.equals(userProfile.email))
            {
                if(it.password.equals(userProfile.password))
                {
                    val userId = it.id
                    val userSession = UserSession("", userId)
                    userSessionRepository.save(userSession)

                    var sessionId = ""
                    val allSessions = userSessionRepository.findAll()
                    allSessions.forEach {
                        if(it.userId.equals(userId))
                        {
                            sessionId = it.sessionId
                        }
                    }
                    return sessionId
                }
                else
                {
                    throw WrongPasswordException("Wrong Password")
                }
            }
        }
        throw UserNotFoundException("Email doesn't exist")
    }
    override fun logout(sessionId: String) {
        if (userSessionRepository.findById(sessionId).isEmpty)
        {
            throw UserNotFoundException()
        }
        userSessionRepository.deleteById(sessionId)
    }
    fun getUser(sessionId: String):UserProfile{
        val optionalUserSession = userSessionRepository.findById(sessionId)
        if (optionalUserSession.isEmpty) {
            throw UserNotFoundException()
        }
        val userSession = optionalUserSession.get()
        val userId = userSession.userId

        if (userRepository.findById(userId).isEmpty) {
            throw UserNotFoundException()
        }

        val user = userRepository.findById(userId).get()
        user.password = ""

        return user
    }

    /**
     * This Function adds the user profile with the given Name as a friend to
     * the logged in user
     * @param id the SessionId of the logged in user
     * @param friendName The userName of the profile that shall be added
     * @return true if adding a friend was successful, false if not
     */
    override fun addFriend(id: String, friendName: String){
        val actUserId: Long = getUserID(id)
        val userList: List<UserProfile> = userRepository.findAll()
        for (user in userList){
            if (user.username == friendName){
                friendRepository.save(Friend(0, actUserId, user.id, friendName +""))
                return
            }
        }
        throw UserNotFoundException("User not found")
    }

    /**
     * This function removes the friend with the given username from the friendlist
     * of the logged in user.
     * @param id The SessionId of the logged in user
     * @param friendName the UserName of the friend that shall be removed
     * @return True if removing was successful, false if not
     */
    override fun removeFriend(id: String, friendName: String){
        val actUserId: Long = getUserID(id)
        var friends: List<Friend> = friendRepository.findAll()
        for (friend in friends){
            if(actUserId == friend.rootUserId && friend.nickname == (friendName)){
                friendRepository.deleteById(friend.id)
                return
            }
        }
        throw UserNotFoundException("Friend doesn't exist")
    }

    /**
     * Returns all friends, associated with the logged in user profile as a User Profile list
     * @param id the SessionId of the logged in User
     * @return a List that contains all User Profiles that are associated with the logged in user
     */
    override fun getFriends(id: String) : List<UserProfile> {
        //Hier alle Freunde eines Benutzers ausgeben
        val friends: List<Friend> = friendRepository.findAll()
        val actUserId: Long = getUserID(id)
        var friendProfiles: MutableList<UserProfile> = mutableListOf()
        for (friend in friends) {
            if (actUserId == friend.rootUserId){
                val optUserProfile: Optional<UserProfile> = userRepository.findById(friend.userID)
                if (optUserProfile.isEmpty){
                    throw UserNotFoundException()
                }
                val userProfile = optUserProfile.get()
                friendProfiles.add(userProfile)
            }
        }
        return friendProfiles.toList()
    }

    /**
     * Selects the UserId based on the SessionID
     * @param id the SessionId of the logged in user
     * @return the UserId to which the SessionId is associated
     */
    private fun getUserID(id: String): Long{
        val optionalUserSession = userSessionRepository.findById(id)
        if (optionalUserSession.isEmpty) {
            throw UserNotFoundException()
        }
        val userSession = optionalUserSession.get()
        val userId = userSession.userId

        if (userRepository.findById(userId).isEmpty) {
            throw UserNotFoundException()
        }
        return userId
    }
}