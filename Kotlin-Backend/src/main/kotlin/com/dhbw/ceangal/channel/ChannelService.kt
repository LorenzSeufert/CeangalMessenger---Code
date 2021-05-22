package com.dhbw.ceangal.channel

import com.dhbw.ceangal.error.ChannelNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class ChannelService: ChannelInterface {
    @Autowired
    lateinit var channelRepository: ChannelRepository

    override fun createChannel(pChannel: Channel): Channel {
        return channelRepository.save(pChannel)

    }

    override fun updateChannelName(pNewName: String, id: Long) {
        val optChannel: Optional<Channel> = channelRepository.findById(id)
        if (optChannel.isEmpty){
            throw ChannelNotFoundException()
        }
        TODO("Not fully implemented")

    }

    override fun sendMessage(pMessage: String) {
        TODO("Not yet implemented")
    }


}