package com.dhbw.ceangal.textChannel

import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import com.dhbw.ceangal.websocket.model.TextChannelRepository
import com.google.gson.Gson
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class TextChannelControllerIntegrationTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var textChannelRepository: TextChannelRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private val user = UserProfile(
        username = "fritz",
        password = "123",
        birthdate = "04.04.2020",
        email = "test@test",
        description = "desc"
    )
    private val gson = Gson()
    private var jsonUser = ""

    @BeforeEach
    fun clearDatabase() {
        textChannelRepository.deleteAll()
        userRepository.deleteAll()
        jsonUser = ""
    }

    @Test
    fun `creates a text channel and returns it as well as Http status OK`() {
        val user = userRepository.save(user)
        jsonUser = gson.toJson(user)
        println(jsonUser)

        val result = mvc
            .perform(
                post("/api/textChannel/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """{
                        "name": "testChannel",
                        "users": [$jsonUser],
                        "messages": []
                    }""".trimIndent()
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        assertEquals(textChannelRepository.count(), 1L)
        val resultString = result.response.contentAsString
        assertThat(resultString, containsString(""""name":"testChannel""""))
        assertThat(resultString, containsString(""""users":[$jsonUser]"""))
        assertThat(resultString, containsString(""""messages":[]"""))
    }

    @Test
    fun `edits a text channel and returns it as well as Http status OK`() {
        val user = UserProfile(
            username = "fritz",
            password = "123",
            birthdate = "04.04.2020",
            email = "test@test",
            description = "desc"
        )

        val result = mvc
            .perform(
                put("/api/textChannel/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""{
                        "name": "testChannel",
                        "users": [],
                        "messages": []
                    }""".trimIndent())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        assertEquals(textChannelRepository.count(), 1L)
    }

}