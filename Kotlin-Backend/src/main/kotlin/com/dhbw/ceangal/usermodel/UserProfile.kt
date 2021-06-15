package com.dhbw.ceangal.usermodel

import com.google.gson.annotations.Expose
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
/**
 * This class represents the user model.
 * @param id
 * @param username
 * @param password (encrypted)
 * @param email
 * @param birthdate
 * @param description
 */
@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    var id: Long = 0L,
    @Expose
    var username: String = "",
    var password: String = "",
    var email: String = "",
    val birthdate: String = "",
    @Expose
    var description: String = ""
)
{

    override fun toString(): String {
        return "UserProfile(id='$id', username='$username', password='$password', email='$email', birthdate='$birthdate', description='$description')"
    }
}