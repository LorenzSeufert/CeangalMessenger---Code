package com.dhbw.ceangal.websocket.model

import com.dhbw.ceangal.error.TextChannelNotFoundException
import com.dhbw.ceangal.error.UserNotFoundException
import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WebSocketChatService {
    @Autowired
    lateinit var textChannelRepository: TextChannelRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun chat(textChannelId: Long, message: Message): Message {
        val textChannel = getTextChannel(textChannelId)
        textChannel.messages.add(message)
        return message
    }

    fun join(textChannelId: Long, message: Message): Message {
        val textChannel = getTextChannel(textChannelId)
        val user = getUser(message.senderId)
        textChannel.users.add(user)
        message.content = "${user.username} joined the channel."
        return message
    }

    fun leave(textChannelId: Long, message: Message): Message {
        val textChannel = getTextChannel(textChannelId)
        val user = getUser(message.senderId)
        textChannel.users.remove(user)
        message.content = "${user.username} left the channel."
        return message
    }

    private fun getTextChannel(textChannelId: Long): TextChannel {
        val optionalTextChannel = textChannelRepository.findById(textChannelId)
        if (optionalTextChannel.isEmpty) {
            throw TextChannelNotFoundException("channel wasn't found in repository")
        }

        return optionalTextChannel.get()
    }

    private fun getUser(userId: Long): UserProfile {
        val optionalUser = userRepository.findById(userId)
        if (optionalUser.isEmpty) {
            throw UserNotFoundException("user wasn't found in repository")
        }

        return optionalUser.get()
    }
}