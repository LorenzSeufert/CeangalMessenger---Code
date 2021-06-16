package com.dhbw.ceangal.usermodel

import com.dhbw.ceangal.websocket.model.TextChannel
import com.google.gson.annotations.Expose
import javax.persistence.*

/**
 * This class represents the user model.
 * @param id
 * @param username
 * @param password (encrypted)
 * @param email
 * @param birthdate
 * @param description
 * @param textChannels
 */


@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Expose
    var id: Long = 0L,
    @Expose
    var username: String = "",
    var password: String = "",
    var email: String = "",
    val birthdate: String = "",
    @Expose
    var description: String = "",
    @Expose
    @OneToMany(targetEntity = TextChannel::class, fetch = FetchType.EAGER)
    val textChannels: MutableList<TextChannel> = mutableListOf()
)
{

    override fun toString(): String {
        return "UserProfile(id=$id, username='$username', password='$password', email='$email', birthdate='$birthdate', description='$description', textChannels=$textChannels)"
    }
}