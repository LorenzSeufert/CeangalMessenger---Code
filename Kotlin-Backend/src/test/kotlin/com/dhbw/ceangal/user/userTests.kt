package com.dhbw.ceangal.user

import com.dhbw.ceangal.friend.Friend
import com.dhbw.ceangal.friend.FriendRepository
import com.dhbw.ceangal.usermodel.UserController
import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import com.dhbw.ceangal.usermodel.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
open class UserTests {
    @Autowired
    private lateinit var mvc: MockMvc
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var friendRepository: FriendRepository
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var userController: UserController
    @Test
    fun login(){

        /*setup Mock*/

        val userProfile = UserProfile(
            username = "Test",
            password = "password",
            birthdate = "01.01.2000",
            email = "test@trashmail.de",
            description = "testuser"
        )
        val user = userRepository.save(userProfile)

        mvc
            .perform(
                MockMvcRequestBuilders.post(
                    "/api/login")
                    .param("username", "Test")
                    .param("password", "password")
                    .content(
                        """{
                        "username": ["Test"],
                        "password": ["password"]
                    }""".trimIndent()
                    )
            )
           .andExpect(status().isOk)


    }

    @Test
    fun addFriend(){
        val userProfile1 = UserProfile(
            0,
            "user1",
            "password",
            "testmali@bsp.com",
            "01.01.2000",
            "blabla"
        )
        val userProfile2 = UserProfile(
            1,
            "user2",
            "password",
            "testmail@bsp.com",
            "01.01.2000",
            "blablablubb"
        )
        val user1 = userRepository.save(userProfile1)
        val user2 = userRepository.save(userProfile2)
        val sessionId = userService.login(userProfile1)

        mvc
            .perform(
                MockMvcRequestBuilders.get("/api/user/addFriend")
                    .header("id", sessionId)
                    .header("friendName","${user2.username}")
            )
            .andExpect(status().isOk)
    }

    @Test
    fun removeFriendTest(){
        val userProfile1 = UserProfile(
            0,
            "user1",
            "password",
            "testmali@bsp.com",
            "01.01.2000",
            "blabla"
        )
        val userProfile2 = UserProfile(
            1,
            "user2",
            "password",
            "testmail@bsp.com",
            "01.01.2000",
            "blablablubb"
        )

        val user1 = userController.createUser(userProfile1)
        val user2 = userController.createUser(userProfile2)
        val sessionId = userService.login(userProfile1)
        val friend: Friend = Friend(0, 0, 1, "user2")
        val friendship= userController.addFriend(sessionId, "user2")

        mvc
            .perform(
                MockMvcRequestBuilders.delete("/user/removeFriend")
                    .header("id", sessionId)
                    .header("friendName", "user2")
            ).andExpect(content().string("Friend was successfully removed"))
            .andExpect(status().isOk)
    }


}