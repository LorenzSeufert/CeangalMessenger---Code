package com.dhbw.ceangal.usermodel

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSessionRepository : JpaRepository<UserSession, String>
