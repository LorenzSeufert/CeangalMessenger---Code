package com.dhbw.ceangal.websocket.testing.data

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id


@Entity
class ActiveWebSocketUser(
    @Id
    val id: String,
    val username: String,
    val connectionTime: Calendar)