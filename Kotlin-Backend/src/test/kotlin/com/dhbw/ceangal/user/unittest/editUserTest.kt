package com.dhbw.ceangal.user.unittest

import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import com.dhbw.ceangal.usermodel.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class editUserTest {
    @Autowired
    private lateinit var userService: UserService

    @Test
    fun editUserTest() {
        var userProfile = UserProfile(username = "User1", password = "password", email = "abc@gmx.de", birthdate = "01.01.2000", description = "description")
        val savedUser = userService.createUser(userProfile)
        val sessionId = userService.login(userProfile)
        val userProfileChange = UserProfile(username = "NewName", password = "", email = "", birthdate = "", description = "")
        val userProfileChanged = userService.editUser(userProfileChange, sessionId)
        val expectedUserProfile = UserProfile(id = savedUser.id, username = "NewName", password = "password", email = "abc@gmx.de", birthdate = "01.01.2000", description = "description")
        assertEquals(expectedUserProfile.toString(), userProfileChanged.toString())
    }

}