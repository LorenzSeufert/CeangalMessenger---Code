package com.dhbw.ceangal.user

import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserService
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class UserControllerTest
{
    @Autowired
    lateinit var userService:UserService
    @Test
    fun createUserTest()
    {
        var userProfile = UserProfile(123321, "User2212", "secretPW", "Schnee@gmx.de", "01.01.2000", "Born and raised in Germany")

        assertEquals(userProfile, userService.createUser(userProfile))
    }
}