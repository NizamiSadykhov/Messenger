package com.nizamisadykhov.messenger.utils.message

import com.stfalcon.chatkit.commons.models.IUser

data class Author(
    val id: Long,
    val username: String,
): IUser {

    override fun getAvatar(): String = ""

    override fun getName(): String {
        return username
    }

    override fun getId(): String {
        return id.toString()
    }
}