package com.dhbw.ceangal.websocket.model

import com.dhbw.ceangal.usermodel.UserProfile
import javax.persistence.*

@Entity
class TextChannel(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long = 0L,
    @ManyToMany
    val users: MutableList<UserProfile> = mutableListOf(),
    //@ElementCollection(targetClass = Message::class, fetch = FetchType.EAGER)
    @OneToMany(targetEntity = Message::class)
    val messages: MutableList<Message> = mutableListOf()
) {
}