package com.dhbw.ceangal.websocket.model

import com.dhbw.ceangal.error.TextChannelNotFoundException
import com.dhbw.ceangal.error.UserNotFoundException
import com.dhbw.ceangal.usermodel.UserProfile
import com.dhbw.ceangal.usermodel.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Service

@Service
class TextChannelService : TextChannelInterface {

    @Autowired
    lateinit var textChannelRepository: TextChannelRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun createTextChannel(textChannel: TextChannel): TextChannel {
        if (textChannel.usersName.isEmpty()) {
            throw UserNotFoundException("No user was passed", BAD_REQUEST)
        }

        val channel = textChannelRepository.save(textChannel)
        textChannel.usersName.forEach { userName ->
            val optionalUser = userRepository.findByUsername(userName)

            if(optionalUser.isEmpty) {
                throw UserNotFoundException("One of the users ($userName) wasn't found", BAD_REQUEST)
            }

            val user = optionalUser.get()
            user.textChannels.add(channel)
            userRepository.save(user)
        }
        return channel
    }

    override fun getTextChannel(id: Long): TextChannel {
        val optionalTextChannel = textChannelRepository.findById(id)

        if (optionalTextChannel.isEmpty) {
            throw TextChannelNotFoundException()
        }

        return optionalTextChannel.get()
    }

    override fun getAllTextChannelsFromUser(id: Long): MutableList<TextChannel> {
        val user = getUser(id)
        return user.textChannels
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

    private fun getUser(id: Long): UserProfile {
        val optionalUser = userRepository.findById(id)

        if (optionalUser.isEmpty) {
            throw UserNotFoundException()
        }

        return optionalUser.get()
    }
}