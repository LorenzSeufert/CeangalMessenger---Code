package com.dhbw.ceangal

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext

@SpringBootTest
open class KotlinBackendApplicationTest(private val applicationContext: ApplicationContext) {

    @Test
    fun contextLoads() {
        MatcherAssert.assertThat(applicationContext, Matchers.`is`(Matchers.notNullValue()))
    }
}
