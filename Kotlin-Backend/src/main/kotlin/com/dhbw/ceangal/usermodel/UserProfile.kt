package com.dhbw.ceangal.usermodel

import com.dhbw.ceangal.websocket.model.TextChannel
import javax.persistence.*

@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long = 0L,
    var username: String,
    val password: String,
    var email: String,
    val birthdate: String,
    var description: String,
    @OneToMany
    val friends: MutableList<UserProfile> = mutableListOf(),
    @ManyToMany
    val chatRooms: MutableList<TextChannel> = mutableListOf()

)
{


    override fun toString(): String {
        return "UserProfile(id=$id, username='$username', password='$password', email='$email', birthdate='$birthdate', description='$description', friends=$friends)"
    }
}