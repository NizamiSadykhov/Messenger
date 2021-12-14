package com.nizamisadykhov.messenger.data.vo

class ConversationVO(
    val conversationId: Long,
    val secondPartyUsername: String,
    val messages: List<MessageVO>
)