package com.dhbw.ceangal.usermodel

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) val username: String,
    val password: String,
    val email: String,
    val birthdate: String
)
{
    override fun toString(): String {
        return "UserProfile(username='$username', password='$password', email='$email', birthdate='$birthdate')"
    }
}