package com.dhbw.ceangal.websocket.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/textChannel")
class TextChannelController {

    @Autowired
    private lateinit var textChannelService: TextChannelService

    @PostMapping(path = ["/create"])
    fun createChannel(@RequestBody textChannel: TextChannel): ResponseEntity<TextChannel> {
        textChannelService.createTextChannel(textChannel)
        return ResponseEntity(textChannel, OK)
    }

    @GetMapping(path = ["/get/{id}"])
    fun getTextChannel(@PathVariable id: Long) : ResponseEntity<TextChannel> {
        return ResponseEntity(textChannelService.getTextChannel(id), OK)
    }

    @GetMapping(path = ["/getAll"])
    fun getAllTextChannelsFromUser(@RequestHeader id: Long) : ResponseEntity<MutableList<TextChannel>> {
        return ResponseEntity(textChannelService.getAllTextChannelsFromUser(id), OK)
    }

    @PutMapping(path = ["/edit"])
    fun editChannel(@RequestBody textChannel: TextChannel) {
        textChannelService.editTextChannel(textChannel)
    }

    @DeleteMapping(path = ["/delete/{textChannelId}"])
    fun deleteChannel(@PathVariable textChannelId: Long) : ResponseEntity<String>{
        textChannelService.deleteTextChannel(textChannelId)
        return ResponseEntity("Channel was deleted successfully", OK)
    }
}