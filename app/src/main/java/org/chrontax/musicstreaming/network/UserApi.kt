package org.chrontax.musicstreaming.network

import okhttp3.ResponseBody
import org.chrontax.musicstreaming.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/user/login")
    suspend fun login(@Body user: User): Response<ResponseBody>

    @POST("/user/register")
    suspend fun register(@Body user: User): Response<ResponseBody>
}