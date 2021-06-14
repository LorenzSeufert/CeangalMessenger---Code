package com.dhbw.ceangal.websocket.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TextChannelRepository : JpaRepository<TextChannel, Long>