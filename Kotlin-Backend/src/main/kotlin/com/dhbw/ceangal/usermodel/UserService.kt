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

        user.username = userProfile.username
        user.email = userProfile.email
        user.description = userProfile.description

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
}