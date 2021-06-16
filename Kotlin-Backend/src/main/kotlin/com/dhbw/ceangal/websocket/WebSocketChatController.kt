package com.dhbw.ceangal.websocket

import com.dhbw.ceangal.error.TextChannelNotFoundException
import com.dhbw.ceangal.websocket.model.Message
import com.dhbw.ceangal.websocket.model.MessageType.*
import com.dhbw.ceangal.websocket.model.WebSocketChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketChatController {

    @Autowired
    lateinit var webSocketChatService: WebSocketChatService

    @MessageMapping("/greeting")
    @SendTo("/topic/greeting")
    fun testGreeting(payload: String): String {
        return payload
    }

    @MessageMapping("/channel/{textChannelId}")
    @SendTo("/topic/channel/{textChannelId}")
    fun sendMessage(@DestinationVariable textChannelId: Long, message: Message): Message {
        if (textChannelId == 0L) {
            throw TextChannelNotFoundException("id is 0")
        }

        return when (message.type) {
            CHAT -> webSocketChatService.chat(textChannelId, message)
            JOIN -> webSocketChatService.join(textChannelId, message)
            LEAVE -> webSocketChatService.leave(textChannelId, message)
        }
    }
}