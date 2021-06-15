package com.dhbw.ceangal.channel

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/api")
class ChannelController
{
    @Autowired
    private lateinit var channelService: ChannelService

    @PostMapping(path = ["/createChannel"])
    fun createChannel(@RequestBody channel: Channel): ResponseEntity<String> {
        channelService.createChannel(channel)
        return ResponseEntity("Channel was created successfully.", HttpStatus.OK)
    }
}

