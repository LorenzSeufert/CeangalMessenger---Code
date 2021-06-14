package com.dhbw.ceangal.websocket

import com.dhbw.ceangal.websocket.model.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketChatController {

    @MessageMapping("/user/{id}")
    @SendTo("/topic/messages")
    fun sendMessage(message: Message) {

    }

}