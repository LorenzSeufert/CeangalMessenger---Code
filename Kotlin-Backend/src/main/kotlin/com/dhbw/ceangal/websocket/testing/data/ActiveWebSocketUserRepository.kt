package com.dhbw.ceangal.websocket.testing.data

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface ActiveWebSocketUserRepository : CrudRepository<ActiveWebSocketUser?, String?> {
    @Query("select DISTINCT(u.username) from ActiveWebSocketUser u where u.username != ?#{principal?.username}")
    fun findAllActiveUsers(): List<String?>?
}