package com.dhbw.ceangal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KotlinBackendApplication
fun main(args: Array<String>) {
	runApplication<KotlinBackendApplication>(*args)
}
