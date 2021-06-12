package com.dhbw.ceangal.channel

import com.dhbw.ceangal.error.ChannelNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
@Service
class ChannelService: ChannelInterface {
    @Autowired
    lateinit var channelRepository: ChannelRepository

    override fun createChannel(pChannel: Channel): Channel {
        return channelRepository.save(pChannel)

    }

    override fun updateChannelName(pNewName: String, id: Long) {
        val optChannel: Optional<Channel> = channelRepository.findById(id)
        if (optChannel.isEmpty){
            throw ChannelNotFoundException()
        }
        val channel: Channel = optChannel.get()
        channel.channelName = pNewName
    }

 /*   override fun sendMessage(pID: Long, pMessage: String) {
        if (channelRepository.findById(pID).isEmpty) {
            throw UserNotFoundException()
        }
        actChannel = channelRepository.findByID(pID)
    }
*/

}