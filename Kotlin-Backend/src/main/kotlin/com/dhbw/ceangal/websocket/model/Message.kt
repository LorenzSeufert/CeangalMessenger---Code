package com.dhbw.ceangal.websocket.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Message(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private var id: Long = 0L,
    var type: MessageType,
    var content: String,
    var sender: String
)