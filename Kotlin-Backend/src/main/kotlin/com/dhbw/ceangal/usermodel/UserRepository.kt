package com.dhbw.ceangal.usermodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserProfile, Long> {
    override fun findById(id: Long): Optional<UserProfile>
    fun findByUsername(userName: String): UserProfile
}
