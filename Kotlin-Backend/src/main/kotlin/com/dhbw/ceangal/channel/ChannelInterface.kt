package com.dhbw.ceangal.channel

import java.util.*

interface ChannelInterface {
    fun createChannel(pChannel: Channel):Channel
    fun updateChannelName(pNewName: String, id: Long)


}