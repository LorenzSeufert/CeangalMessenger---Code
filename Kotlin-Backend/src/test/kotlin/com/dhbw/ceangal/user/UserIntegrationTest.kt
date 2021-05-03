package com.dhbw.ceangal.user

import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserIntegrationTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun clearDatabase() {
        userRepository.deleteAll()
    }

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

        assertEquals(userRepository.count(), 1L)
    }

    @Test
    fun `create a user with incorrect data returns http status bad request`() {
        mvc
            .perform(post("/api/user/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                        "usernam: 3333
                    }""".trimIndent())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)

        assertEquals(userRepository.count(), 0L)
    }

    @Test
    fun `edit User with correct data returns http status ok and the changed user`() {
        val oldUser = userRepository.save(UserProfile(
            username = "testUser",
            password = "1234",
            email = "test@test.com",
            birthdate= "04.06.1991",
            description = "testDesc"
        ))

        val userId = oldUser.id
        val changedDescription = "changedDescription"

        mvc
            .perform(put("/api/user/editUser/$userId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                        "username": "testUser",
                        "password": "1234",
                        "email": "test@test.com",
                        "birthdate": "04.06.1991",
                        "description": "$changedDescription"
                    }""".trimIndent())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)

        assertEquals(userRepository.count(), 1L)
        assertEquals(userRepository.findById(userId).get().description, changedDescription)
    }

    @Test
    fun `deletes the user from the repository returns http status ok`() {
        val oldUser = userRepository.save(UserProfile(
            username = "testUser",
            password = "1234",
            email = "test@test.com",
            birthdate= "04.06.1991",
            description = "testDesc"
        ))

        val userId = oldUser.id

        mvc
            .perform(delete("/api/user/deleteUser/$userId"))
            .andExpect(status().isOk)

        assertEquals(userRepository.count(), 0L)
    }

}