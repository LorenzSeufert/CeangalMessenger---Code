package com.dhbw.ceangal.friend

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Friend (
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    val id: Long = 0L,
    val friendID: Long,
    val userID: Long,
    var nickname: String
) {


}