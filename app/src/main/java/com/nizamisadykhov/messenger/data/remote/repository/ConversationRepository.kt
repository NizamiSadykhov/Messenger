package com.nizamisadykhov.messenger.data.remote.repository

import com.nizamisadykhov.messenger.data.vo.ConversationListVO
import com.nizamisadykhov.messenger.data.vo.ConversationVO
import io.reactivex.Observable

interface ConversationRepository {
    fun findConversationById(id: Long): Observable<ConversationVO>
    fun all(): Observable<ConversationListVO>
}