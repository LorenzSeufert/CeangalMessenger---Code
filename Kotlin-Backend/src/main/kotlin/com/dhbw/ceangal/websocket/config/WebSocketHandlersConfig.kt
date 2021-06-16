/*
package com.dhbw.ceangal.websocket.config

import com.dhbw.ceangal.websocket.testing.WebSocketConnectHandler
import com.dhbw.ceangal.websocket.testing.WebSocketDisconnectHandler
import com.dhbw.ceangal.websocket.testing.data.ActiveWebSocketUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.session.Session

@Configuration
open class WebSocketHandlersConfig<S : Session> {
    @Bean
    open fun webSocketConnectHandler(
        messagingTemplate: SimpMessageSendingOperations,
        repository: ActiveWebSocketUserRepository
    ): WebSocketConnectHandler<S> {
        return WebSocketConnectHandler(messagingTemplate, repository)
    }

    @Bean
    open fun webSocketDisconnectHandler(
        messagingTemplate: SimpMessageSendingOperations,
        repository: ActiveWebSocketUserRepository
    ): WebSocketDisconnectHandler<S> {
        return WebSocketDisconnectHandler(messagingTemplate, repository)
    }
}*/
