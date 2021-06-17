package com.dhbw.ceangal.user

import com.dhbw.ceangal.usermodel.UserProfile
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
open class UserTests {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun login(){

        /*setup Mock*/
        val userProfile: UserProfile = UserProfile(0, "test", "password", "test@trashmail.de", "")

        mvc
            .perform(MockMvcRequestBuilders.get("/api/login", "test", "password" ))
            .andExpect(content()to .
            .andExpect(status().isOk)

    }

}