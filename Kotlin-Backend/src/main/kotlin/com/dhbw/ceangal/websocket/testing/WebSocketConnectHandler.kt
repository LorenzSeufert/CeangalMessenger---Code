package com.dhbw.ceangal.websocket.testing

import com.dhbw.ceangal.websocket.testing.data.ActiveWebSocketUser
import com.dhbw.ceangal.websocket.testing.data.ActiveWebSocketUserRepository
import org.springframework.context.ApplicationListener
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.socket.messaging.SessionConnectEvent
import java.security.Principal
import java.util.*


class WebSocketConnectHandler<S>(
    messagingTemplate: SimpMessageSendingOperations,
    repository: ActiveWebSocketUserRepository
) : ApplicationListener<SessionConnectEvent> {
    private val repository: ActiveWebSocketUserRepository
    private val messagingTemplate: SimpMessageSendingOperations
    override fun onApplicationEvent(event: SessionConnectEvent) {
        val headers: MessageHeaders = event.getMessage().getHeaders()
        val user: Principal = SimpMessageHeaderAccessor.getUser(headers) ?: return
        val id: String = SimpMessageHeaderAccessor.getSessionId(headers)!!
        repository.save(ActiveWebSocketUser(id, user.name, Calendar.getInstance()))
        messagingTemplate.convertAndSend("/topic/friends/signin", listOf(user.name))
    }

    init {
        this.messagingTemplate = messagingTemplate
        this.repository = repository
    }
}