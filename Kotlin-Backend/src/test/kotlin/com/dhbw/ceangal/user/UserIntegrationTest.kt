package com.dhbw.ceangal.user

import com.dhbw.ceangal.friend.Friend
import com.dhbw.ceangal.friend.FriendRepository
import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import com.dhbw.ceangal.usermodel.UserSessionRepository
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.MultiValueMap
import javax.transaction.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(properties = ["feature.application-event-listeners=false"])
@ActiveProfiles("local,local-h2")
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
class UserIntegrationTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userSessionRepository: UserSessionRepository

    @Autowired
    private lateinit var friendRepository: FriendRepository

    @BeforeEach
    fun clearDatabase() {
        userRepository.deleteAll()
        userSessionRepository.deleteAll()
        friendRepository.deleteAll()
    }

    @Test
    fun `creates a user with correct data returns http status ok`() {
        mvc
            .perform(
                post("/api/user/createUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """{
                        "username": "testUser",
                        "password": "1234",
                        "email": "test@test.com",
                        "birthdate": "04.06.1991",
                        "description": "testDesc"
                    }""".trimIndent()
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)

        assertEquals(userRepository.count(), 1L)
    }

    @Test
    fun `create a user with incorrect data returns http status bad request`() {
        mvc
            .perform(
                post("/api/user/createUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """{
                        "usernam: 3333
                    }""".trimIndent()
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest)

        assertEquals(userRepository.count(), 0L)
    }

    @Test
    fun `edit User with correct data returns http status ok and the changed user`() {
        val oldUser = saveUser()

        val changedDescription = "changedDescription"

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${oldUser.username}",
                    "password": "${oldUser.password}",
                    "email": "${oldUser.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = result.response.getHeader("sessionId")

        mvc
            .perform(
                put("/api/user/editUser")
                    .header("id", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        """{
                        "username": "${oldUser.username}",
                        "password": "${oldUser.password}",
                        "email": "${oldUser.email}",
                        "birthdate": "${oldUser.birthdate}",
                        "description": "$changedDescription"
                    }""".trimIndent()
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk)

        assertEquals(userRepository.count(), 1L)
        assertEquals(userRepository.findById(oldUser.id).get().description, changedDescription)
    }

    @Test
    fun `deletes the user from the repository returns http status ok`() {
        val user = saveUser()

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${user.username}",
                    "password": "${user.password}",
                    "email": "${user.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = result.response.getHeader("sessionId")

        mvc
            .perform(
                delete("/api/user/deleteUser")
                    .header("id", id)
            )
            .andExpect(status().isOk)

        assertEquals(userRepository.count(), 0L)
    }

    @Test
    fun `login user method, returns user sessionId and http status ok`() {
        val user = saveUser()

        assertEquals(0L, userSessionRepository.count())

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${user.username}",
                    "password": "${user.password}",
                    "email": "${user.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = result.response.getHeader("sessionId")
        val returnUser = result.response.contentAsString

        assertEquals(1L, userSessionRepository.count())
        assertThat(returnUser, containsString(""""username":"${user.username}""""))
        assertThat(returnUser, containsString(""""password":"${user.password}""""))
        assertThat(returnUser, containsString(""""email":"${user.email}""""))
        assertNotNull(id)
    }

    @Test
    fun `logout method returns http status ok`() {
        val user = saveUser()

        assertEquals(0L, userSessionRepository.count())

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${user.username}",
                    "password": "${user.password}",
                    "email": "${user.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        assertEquals(1L, userSessionRepository.count())

        val id = result.response.getHeader("sessionId")

        mvc.perform(
            delete("/api/user/logout")
                .header("id", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        assertEquals(0L, userSessionRepository.count())

    }

    @Test
    fun `add friend method adds friend to friend repository and returns http status ok`() {
        val user = saveUser()
        val friend = saveFriend()

        assertEquals(0L, friendRepository.count())

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${user.username}",
                    "password": "${user.password}",
                    "email": "${user.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = result.response.getHeader("sessionId")
        val headers = HttpHeaders()
        headers.set("id", id)
        headers.set("friendName", friend.username)

        val result2 = mvc.perform(
            get("/api/user/addFriend")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content("")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val resultAsString = result2.response.contentAsString

        assertEquals(1L, friendRepository.count())
        assertThat(resultAsString, containsString("Friend has been added successfully."))
    }

    @Test
    fun `remove Friend method deletes friend from repository and returns http status OK`() {
        val user = saveUser()
        val friend = saveFriend()

        assertEquals(0L, friendRepository.count())

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${user.username}",
                    "password": "${user.password}",
                    "email": "${user.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = result.response.getHeader("sessionId")
        val headers = HttpHeaders()
        headers.set("id", id)
        headers.set("friendName", friend.username)

        friendRepository.save(Friend(rootUserId = user.id, userID = friend.id, nickname = friend.username))
        assertEquals(1L, friendRepository.count())

        val result2 = mvc.perform(
            delete("/api/user/removeFriend")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content("")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val resultAsString = result2.response.contentAsString

        assertEquals(0L, friendRepository.count())
        assertThat(resultAsString, containsString("Friend was successfully removed"))
    }

    @Test
    fun `get friends method is called returns friends as Json String and http OK`() {
        val user = saveUser()
        val friend = saveFriend()

        assertEquals(0L, friendRepository.count())

        val result = mvc.perform(
            post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                    "username": "${user.username}",
                    "password": "${user.password}",
                    "email": "${user.email}"
                }""".trimIndent()
                )
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        val id = result.response.getHeader("sessionId")
        val headers = HttpHeaders()
        headers.set("id", id)

        friendRepository.save(Friend(rootUserId = user.id, userID = friend.id, nickname = friend.username))
        assertEquals(1L, friendRepository.count())

        val result2 = mvc.perform(
            get("/api/user/getFriends")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content("")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(print())
            .andReturn()

        val resultAsString = result2.response.contentAsString

        assertEquals(1L, friendRepository.count())
        assertThat(resultAsString, containsString(""""id":${friend.id}"""))
        assertThat(resultAsString, containsString(""""username":"${friend.username}""""))
        assertThat(resultAsString, containsString(""""description":"${friend.description}""""))
        assertThat(resultAsString, containsString(""""textChannels":${friend.textChannels}"""))
    }

    private fun saveUser(): UserProfile {
        return userRepository.save(
            UserProfile(
                username = "testUser",
                password = "1234",
                email = "test@test.com",
                birthdate = "04.06.1991",
                description = "testDesc"
            )
        )
    }

    private fun saveFriend(): UserProfile {
        return userRepository.save(
            UserProfile(
                username = "Lukas",
                password = "1234",
                email = "test@test.com",
                birthdate = "04.06.1991",
                description = "testDesc"
            )
        )
    }

}