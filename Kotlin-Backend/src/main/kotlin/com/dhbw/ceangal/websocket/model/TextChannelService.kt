package com.dhbw.ceangal.websocket.model

import com.dhbw.ceangal.error.TextChannelNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TextChannelService : TextChannelInterface {

    @Autowired
    lateinit var textChannelRepository: TextChannelRepository

    override fun createTextChannel(textChannel: TextChannel): TextChannel {
        return textChannelRepository.save(textChannel)
    }

    override fun getTextChannel(id: Long): TextChannel {
        val optionalTextChannel = textChannelRepository.findById(id)

        if (optionalTextChannel.isEmpty) {
            throw TextChannelNotFoundException()
        }

        return optionalTextChannel.get()
    }

    override fun editTextChannel(textChannel: TextChannel): TextChannel {
        val channel = getTextChannel(textChannel.id)
        channel.name = textChannel.name

        return textChannelRepository.save(channel)
    }

    override fun deleteTextChannel(id: Long) {
        if (textChannelRepository.findById(id).isEmpty) {
            throw TextChannelNotFoundException()
        }

        textChannelRepository.deleteById(id)
    }
}