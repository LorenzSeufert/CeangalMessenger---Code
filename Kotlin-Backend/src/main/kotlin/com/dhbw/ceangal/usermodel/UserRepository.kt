package com.dhbw.ceangal.usermodel

import com.dhbw.ceangal.example.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserProfile, String>
