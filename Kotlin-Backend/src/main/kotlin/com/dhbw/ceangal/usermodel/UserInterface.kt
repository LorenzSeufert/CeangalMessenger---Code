package com.dhbw.ceangal.usermodel

interface UserInterface {
    fun createUser(userProfile: UserProfile): UserProfile
    fun editUser(userProfile: UserProfile, id: String): UserProfile
    fun deleteUser(id: String)
    fun login(userProfile: UserProfile): String
    fun logout(sessionId: String)
    fun addFriend(id: Long, friendName: String) : Boolean
    fun removeFriend(id: String, friendName: String) : Boolean
    fun getFriends(id: String) : List<UserProfile>
}