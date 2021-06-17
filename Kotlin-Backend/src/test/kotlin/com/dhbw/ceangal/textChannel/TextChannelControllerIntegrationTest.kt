package com.dhbw.ceangal.textChannel

import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import com.dhbw.ceangal.websocket.model.TextChannel
import com.dhbw.ceangal.websocket.model.TextChannelRepository
import com.google.gson.Gson
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
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

    private val testChannel = TextChannel(
        name = "testChannel",
        usersName = mutableSetOf("fritz"),
    )

    @BeforeEach
    fun clearDatabase() {
        userRepository.deleteAll()
        textChannelRepository.deleteAll()
    }

    @Test
    fun `creates a text channel and returns it as well as Http status OK`() {
        val userProfile = UserProfile(
            username = "Lukas",
            password = "123",
            birthdate = "04.04.2020",
            email = "test@test",
            description = "desc"
        )
        val user = userRepository.save(user)
        val user2 = userRepository.save(userProfile)

        val result = mvc
            .perform(
                post("/api/textChannel/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """{
                        "name": "testChannel",
                        "usersName": ["${user.username}","${user2.username}"],
                        "messages": [
                            {
                                "type": "JOIN",
                                "content": "",
                                "senderId": "${user.id}",
                                "sender": "${user.username}"
                            }
                        ]
                    }""".trimIndent()
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val resultString = result.response.contentAsString
        assertEquals(textChannelRepository.count(), 1L)
        assertThat(resultString, containsString(""""name":"testChannel""""))
        assertThat(resultString, containsString(""""usersName":["${user.username}","${user2.username}"]"""))
        assertThat(resultString, containsString(""""type":"JOIN""""))
        assertThat(resultString, containsString(""""senderId":${user.id}"""))
        assertThat(resultString, containsString(""""sender":"${user.username}""""))
    }

    @Test
    fun `gets a text channel and returns it as well as Http status OK`() {
        val user = userRepository.save(user)
        val textChannel = textChannelRepository.save(testChannel)

        val result = mvc
            .perform(
                get("/api/textChannel/get/${textChannel.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val resultString = result.response.contentAsString
        assertEquals(textChannelRepository.count(), 1L)
        assertThat(resultString, containsString(""""name":"${textChannel.name}""""))
        assertThat(resultString, containsString(""""usersName":["${user.username}"]"""))
    }

    @Test
    fun `gets all text channel and returns them as well as Http status OK`() {
        val user = userRepository.save(user)
        val textChannel = textChannelRepository.save(testChannel)

        val test2Channel = TextChannel(name = "test2Channel", usersName = mutableSetOf("fritz"))
        val text2Channel = textChannelRepository.save(test2Channel)

        user.textChannels.add(textChannel)
        user.textChannels.add(text2Channel)

        userRepository.save(user)

        val result = mvc
            .perform(
                get("/api/textChannel/getAllFromUser")
                    .header("id", user.id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val resultString = result.response.contentAsString
        assertEquals(textChannelRepository.count(), 2L)
        assertThat(resultString, containsString(""""usersName":["${user.username}"]"""))
        assertThat(resultString, containsString(""""name":"${textChannel.name}""""))
        assertThat(resultString, containsString(""""name":"${text2Channel.name}""""))
    }

    @Test
    fun `edits a text channel and returns it as well as Http status OK`() {
        val user = userRepository.save(user)
        val textChannel = textChannelRepository.save(testChannel)

        val result = mvc
            .perform(
                put("/api/textChannel/edit")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """{
                        "id": ${textChannel.id},
                        "name": "editedChannel",
                        "usersName": ["fritz"],
                        "messages": ${textChannel.messages}
                    }""".trimIndent()
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val resultString = result.response.contentAsString
        assertEquals(textChannelRepository.count(), 1L)
        assertThat(resultString, containsString(""""name":"editedChannel""""))
        assertThat(resultString, containsString(""""usersName":["${user.username}"]"""))
    }

    @Test
    fun `deletes a text channel and returns Http status OK`() {
        val textChannel = textChannelRepository.save(testChannel)

        val result = mvc
            .perform(
                delete("/api/textChannel/delete/${textChannel.id}")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("")
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andReturn()

        val resultString = result.response.contentAsString
        assertEquals(textChannelRepository.count(), 0L)
        assertThat(resultString, containsString("""Channel was deleted successfully"""))
    }

}