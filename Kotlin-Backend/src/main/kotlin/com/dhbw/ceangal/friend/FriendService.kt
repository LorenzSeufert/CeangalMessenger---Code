package com.dhbw.ceangal.friend

import com.dhbw.ceangal.error.FriendNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class FriendService: FriendInterface {
    @Autowired
    lateinit var friendRepository: FriendRepository

    override fun addFriend(pFriend: Friend): Friend{
        return friendRepository.save(pFriend)
    }
    override fun removeFriend(pFriendId: Long){
            if (friendRepository.findById(pFriendId).isEmpty){
                throw FriendNotFoundException()
            }
            friendRepository.deleteById(pFriendId)
    }
    override fun addNickname(pFriendId: Long, pNickname: String): Friend{
        val optFriend = friendRepository.findById(pFriendId)
        if (optFriend.isEmpty){
            throw FriendNotFoundException()
        }
        val friend = optFriend.get()
        friend.nickname = pNickname
        return friendRepository.save(friend)
    }
}