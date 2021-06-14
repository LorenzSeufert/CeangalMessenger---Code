package com.dhbw.ceangal.websocket.model

interface TextChannelInterface {
    fun createTextChannel(textChannel: TextChannel): TextChannel
    fun editTextChannel(textChannel: TextChannel, id: Long): TextChannel
    fun deleteTextChannel(id: Long)
}