package com.dhbw.ceangal.usermodel

interface UserInterface {
    fun createUser(userProfile: UserProfile): UserProfile
    fun editUser(userProfile: UserProfile, id: String): UserProfile
    fun deleteUser(id: String)
    fun login(userProfile: UserProfile): String
    fun logout(sessionId: String)
}