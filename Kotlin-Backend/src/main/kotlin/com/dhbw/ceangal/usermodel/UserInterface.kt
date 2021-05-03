package com.dhbw.ceangal.usermodel

interface UserInterface {
    fun createUser(userProfile: UserProfile): UserProfile
    fun editUser(userProfile: UserProfile, id: Long): UserProfile
    fun deleteUser(id: Long)
}