package com.dhbw.ceangal.websocket

import com.dhbw.ceangal.websocket.model.Message
import com.dhbw.ceangal.websocket.model.MessageType
import com.dhbw.ceangal.websocket.model.TextChannel
import com.dhbw.ceangal.websocket.model.TextChannelRepository
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.Transport
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import java.lang.reflect.Type
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private const val SUBSCRIBE_TEST_GREETING = "/topic/greeting"
private const val SEND_TEST_GREETING = "/app/greeting"

private const val SUBSCRIBE_TO_TEXT_CHANNEL: String = "/topic/channel/"
private const val SEND_MESSAGE_TO_TEXT_CHANNEL: String = "/app/channel/"

private val messageQueue: BlockingQueue<Message> = ArrayBlockingQueue(1)
private val greetingQueue: BlockingQueue<String> = ArrayBlockingQueue(1)

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerIntegrationTest {
    @LocalServerPort
    private lateinit var port: Integer
    private lateinit var URL: String
    private val textChannel = TextChannel(name = "test")
    private var channelId: Long = 0L
    private val stompClient: WebSocketStompClient = WebSocketStompClient(SockJsClient(createTransportClient()))
    private val channelStompFrameHandler = ChannelStompFrameHandler()
    private val greetingStompFrameHandler = GreetingStompFrameHandler()
    private val gson = Gson()
    private val message = Message(123L, MessageType.CHAT, "hello", 123L, "fritz")
    private val jsonMessage = gson.toJson(message)
    private val handler = WebSocketStompHandler()

    @Autowired
    lateinit var textChannelRepository: TextChannelRepository

    @Before
    fun setup() {
        URL = "ws://localhost:$port/messages"
        val channel = textChannelRepository.save(textChannel)
        channelId = channel.id
    }

    @Test
    fun `send a greeting`() {
        stompClient.messageConverter = StringMessageConverter()

        val stompSession = stompClient.connect(URL, handler).get(3, SECONDS)

        stompSession.subscribe(SUBSCRIBE_TEST_GREETING, greetingStompFrameHandler)
        stompSession.send(SEND_TEST_GREETING, "hello")
        assertEquals("hello", greetingQueue.poll(3, SECONDS))
    }

    @Test
    fun `send message to text channel and get those messages back to the users`() {
        println(channelId)
        stompClient.messageConverter = MappingJackson2MessageConverter()

        val stompSession = stompClient.connect(URL, handler).get(3, SECONDS)

        stompSession.subscribe(SUBSCRIBE_TO_TEXT_CHANNEL + channelId, channelStompFrameHandler)
        stompSession.send(SEND_MESSAGE_TO_TEXT_CHANNEL + channelId, message)
        val receivedMessage = messageQueue.poll(3, SECONDS)
        assertEquals("hello", receivedMessage?.content)

        println(message)
        assertNotNull(message)
    }


    private fun createTransportClient(): List<Transport> {
        val transports = mutableListOf<Transport>()
        transports.add(WebSocketTransport(StandardWebSocketClient()))
        transports.add(RestTemplateXhrTransport());
        return transports
    }

    class GreetingStompFrameHandler : StompFrameHandler {
        override fun getPayloadType(headers: StompHeaders): Type {
            return String::class.javaObjectType
        }

        override fun handleFrame(headers: StompHeaders, greeting: Any?) {
            greetingQueue.add(greeting as String)
            println(greeting)
        }
    }

    class ChannelStompFrameHandler : StompFrameHandler {
        override fun getPayloadType(headers: StompHeaders): Type {
            return Message::class.javaObjectType
        }

        override fun handleFrame(headers: StompHeaders, message: Any?) {
            messageQueue.add(message as Message)
            println(message)
        }
    }
}