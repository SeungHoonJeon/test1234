package com.example.ye0n
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface SignupService {
    @FormUrlEncoded
    @POST("/app_Signup")
    fun requestLogin(
        @Field("id") id:String,
        @Field("password") password:String,
        @Field("passwordCheck") passwordCheck:String
    ) : Call<Signup>
}