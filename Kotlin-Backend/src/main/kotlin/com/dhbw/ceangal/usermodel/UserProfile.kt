package com.dhbw.ceangal.usermodel

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long = 0L,
    var username: String,
    val password: String,
    var email: String,
    val birthdate: String,
    var description: String
)
{

    override fun toString(): String {
        return "UserProfile(id='$id', username='$username', password='$password', email='$email', birthdate='$birthdate', description='$description')"
    }
}