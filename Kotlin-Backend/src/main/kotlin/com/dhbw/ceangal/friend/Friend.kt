package com.dhbw.ceangal.friend

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Friend (
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    val id: Long = 0L,
    val rootUserId: Long = 0L,
    val userID: Long = 0L,
    var nickname: String = ""
    ) {


}