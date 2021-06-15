package com.dhbw.ceangal.friend

interface FriendInterface {
    fun addFriend(pFriend: Friend):Friend
    fun removeFriend(pFriendId: Long)
    fun addNickname(pFriendId: Long, pNickname: String): Friend



}