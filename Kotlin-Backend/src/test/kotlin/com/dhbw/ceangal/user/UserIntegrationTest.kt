package com.dhbw.ceangal.user

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserIntegrationTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `creates a user with correct data returns http status ok`() {
        mvc
            .perform(post("/api/user/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                        "username": "testUser",
                        "password": "1234",
                        "email": "test@test.com",
                        "birthdate": "04.06.1991",
                        "description": "testDesc"
                    }""".trimIndent())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun `create a user with incorrect data returns http status bad request`() {
        mvc
            .perform(post("/api/user/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                        "username": 3333
                    }""".trimIndent())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }


}