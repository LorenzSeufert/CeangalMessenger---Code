package com.dhbw.ceangal.example

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
open class EmployeeIntegrationTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun getEmployee_returnsHello() {
        mvc
            .perform(get("/api/board"))
            .andExpect(status().isOk)
            .andExpect(content().string("hello"))
            .andDo(print())
    }
}
