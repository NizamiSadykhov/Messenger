package com.nizamisadykhov.messenger.data.remote.repository

import com.nizamisadykhov.messenger.data.vo.UserListVO
import com.nizamisadykhov.messenger.data.vo.UserVO
import io.reactivex.Observable

interface UserRepository {
    fun findById(id: Long): Observable<UserVO>
    fun all(): Observable<UserListVO>
    fun echoDetails(): Observable<UserVO>
}