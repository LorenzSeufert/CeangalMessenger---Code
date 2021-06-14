package com.dhbw.ceangal.websocket.testing

import com.dhbw.ceangal.websocket.testing.data.ActiveWebSocketUserRepository
import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.socket.messaging.SessionDisconnectEvent


class WebSocketDisconnectHandler<S>(
    private val messagingTemplate: SimpMessageSendingOperations,
    private val repository: ActiveWebSocketUserRepository
) : ApplicationListener<SessionDisconnectEvent> {
    override fun onApplicationEvent(event: SessionDisconnectEvent) {
        val id: String = event.sessionId
        this.repository.findById(id).ifPresent { user ->
            repository.deleteById(id)
            messagingTemplate.convertAndSend("/topic/friends/signout", listOf(user.username))
        }
    }
}