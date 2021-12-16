package com.nizamisadykhov.messenger.service

import com.nizamisadykhov.messenger.data.remote.request.LoginRequestObject
import com.nizamisadykhov.messenger.data.remote.request.MessageRequestObject
import com.nizamisadykhov.messenger.data.remote.request.UserRequestObject
import com.nizamisadykhov.messenger.data.vo.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val AUTHORIZATION = "Authorization"
private const val AWS_URL = "http://messengerapi-env-1.eba-qipnct7i.us-east-2.elasticbeanstalk.com"

interface MessengerApiService {
    @POST("login")
    @Headers("Content-Type: application.json")
    fun login(@Body user: LoginRequestObject): Observable<Response<ResponseBody>>

    @POST("users/registration")
    fun createUser(@Body user: UserRequestObject): Observable<UserVO>

    @GET("users")
    fun listUsers(@Header(AUTHORIZATION) authorization: String): Observable<UserListVO>

    @GET("users/{userId}")
    fun showUser(
        @Path("userId") userId: Long,
        @Header(AUTHORIZATION) authorization: String
    ): Observable<UserVO>

    @GET("users/details")
    fun echoDetails(@Header(AUTHORIZATION) authorization: String): Observable<UserVO>

    @POST("messages")
    fun createMessage(
        @Body messageRequestObject: MessageRequestObject,
        @Header(AUTHORIZATION) authorization: String
    ): Observable<MessageVO>

    @GET("conversations")
    fun listConversation(@Header(AUTHORIZATION) authorization: String): Observable<ConversationListVO>

    @GET("conversation/@{conversation}")
    fun showConversation(
        @Path("conversationId") conversationId: Long,
        @Header(AUTHORIZATION) authorization: String
    ): Observable<ConversationVO>

    companion object Factory {
        private var service: MessengerApiService? = null

        fun getInstance(): MessengerApiService {
            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(AWS_URL)
                    .build()
                service = retrofit.create(MessengerApiService::class.java)
            }
            return service as MessengerApiService
        }
    }
}