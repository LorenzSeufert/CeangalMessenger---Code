package com.dhbw.ceangal.websocket

import com.dhbw.ceangal.error.TextChannelNotFoundException
import com.dhbw.ceangal.websocket.model.Message
import com.dhbw.ceangal.websocket.model.TextChannelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketChatController {

    @Autowired
    lateinit var textChannelRepository: TextChannelRepository

    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    fun testGreeting(payload: String): String {
        return payload
    }

    @MessageMapping("/channel/{textChannelId}")
    @SendTo("/topic/channel/{textChannelId}")
    fun sendMessage(
        @DestinationVariable textChannelId: Long,
        message: Message
    ): Message { //TODO does id convert right?
        if (textChannelId == 0L) {
            throw TextChannelNotFoundException("id is 0")
        }
        val optionalTextChannel = textChannelRepository.findById(textChannelId)
        if (optionalTextChannel.isEmpty) {
            throw TextChannelNotFoundException("channel wasn't found in repository")
        }

        val textChannel = optionalTextChannel.get()
        textChannel.messages.add(message)
        return message
    }
}