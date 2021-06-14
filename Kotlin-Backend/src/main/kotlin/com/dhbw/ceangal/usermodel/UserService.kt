package com.dhbw.ceangal.usermodel

import com.dhbw.ceangal.error.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * This class executes user commands. It functions for creating, editing and deleting a user.
 */
@Service
class UserService: UserInterface {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userSessionRepository: UserSessionRepository

    /**
     * This function adds the user to the Repository
     *
     * @param userProfile the user to be created
     * @return the saved UserProfile
     */
    override fun createUser(userProfile: UserProfile): UserProfile {
        return userRepository.save(userProfile)
    }

    /**
     * This function edits the user in the Repository
     *
     * @param userProfile the user to be created
     * @param id the id of the user whose data should be changed
     * @return the saved UserProfile
     */
    override fun editUser(userProfile: UserProfile, id: Long): UserProfile {
        val optionalUser = userRepository.findById(id)

        if (optionalUser.isEmpty) {
            throw UserNotFoundException()
        }
        val user = optionalUser.get()

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
    override fun deleteUser(id: Long) {
        if (userRepository.findById(id).isEmpty) {
            throw UserNotFoundException()
        }

        userRepository.deleteById(id)
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
                    return "1"
                }
            }
        }
        return "0"
    }
    override fun logout(sessionId: String) {

    }
}