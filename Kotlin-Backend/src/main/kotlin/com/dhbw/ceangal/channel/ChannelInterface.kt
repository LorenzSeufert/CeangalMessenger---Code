package com.dhbw.ceangal.channel

interface ChannelInterface {
    fun createChannel(pChannel: Channel):Channel
    fun updateChannelName(pNewName: String, id: Long)
    fun sendMessage(pMessage: String)


}