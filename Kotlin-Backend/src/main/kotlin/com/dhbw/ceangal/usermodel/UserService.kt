package com.dhbw.ceangal.usermodel

import com.dhbw.ceangal.error.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService: UserInterface {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun createUser(userProfile: UserProfile): UserProfile {
        return userRepository.save(userProfile)
    }

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

    override fun deleteUser(id: Long) {
        if (userRepository.findById(id).isEmpty) {
            throw UserNotFoundException()
        }

        userRepository.deleteById(id)
    }
}