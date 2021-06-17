package com.dhbw.ceangal.user.unittest;

import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@SpringBootTest
public class delteUserTest {
    @Autowired
    lateinit var userService: UserService

    @Test
    fun deleteUserTest() {
        var userProfile = UserProfile(1, "User1", "password", "abc@gmx.de", "01.01.2000", "description")
        userService.createUser(userProfile)
        val sessionId = userService.login(userProfile)
        userService.deleteUser(sessionId)
    }
}
