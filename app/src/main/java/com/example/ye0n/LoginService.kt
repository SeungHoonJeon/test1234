package com.example.ye0n

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService{
    @FormUrlEncoded
    @POST("/app_login")
    fun requestLogin(
        @Field("id") id:String,
        @Field("password") password:String
    ) : Call<Login>
}