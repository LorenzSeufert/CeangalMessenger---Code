package com.dhbw.ceangal.friend

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Friend (
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    val id: Long = 0L,
    val rootUserId: Long,
    val userID: Long,
    var nickname: String = ""
    ) {


}