package com.dhbw.ceangal.user.unittest

import com.dhbw.ceangal.usermodel.UserController
import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class deleteUserRestTest {
    @Autowired
    lateinit var userController: UserController
    @Autowired
    lateinit var userService: UserService

    @Test
    fun deleteUserTest() {
        var userProfile = UserProfile(1, "User1", "password", "abc@gmx.de", "01.01.2000", "description")
        userController.createUser(userProfile)
        val sessionId = userService.login(userProfile)
        assertEquals(HttpStatus.OK, userController.deleteUser(sessionId).statusCode)
    }
}