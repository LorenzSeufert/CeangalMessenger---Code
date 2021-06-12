package com.dhbw.ceangal.channel

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
 class Channel (
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    val id: Long = 0L,
    var channelName: String,
    var description: String
    ){

    fun newMessage(pUserID: Long, pMessage: String) {

    }


}

