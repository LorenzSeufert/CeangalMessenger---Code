package com.dhbw.ceangal.websocket.model

import javax.persistence.*

@Entity
class TextChannel(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long = 0L,
    var name: String,
    @ElementCollection
    val usersName: MutableSet<String> = mutableSetOf(),
    @OneToMany(targetEntity = Message::class, fetch = FetchType.EAGER)
    val messages: MutableList<Message> = mutableListOf()
) {
}