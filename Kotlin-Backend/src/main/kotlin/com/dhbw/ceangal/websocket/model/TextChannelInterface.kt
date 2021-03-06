package com.dhbw.ceangal.websocket.model

interface TextChannelInterface {
    fun createTextChannel(textChannel: TextChannel): TextChannel
    fun getTextChannel(id: Long): TextChannel
    fun getAllTextChannelsFromUser(id: Long): MutableList<TextChannel>
    fun editTextChannel(textChannel: TextChannel): TextChannel
    fun deleteTextChannel(id: Long)
}