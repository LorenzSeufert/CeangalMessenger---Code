package com.dhbw.ceangal.usermodel

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

/**
 * This class represents a User session.
 * @param sessionId
 * @param userId
 */
@Entity
class UserSession(
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    var sessionId: String = "",
    var userId: Long = 0
)
{

}