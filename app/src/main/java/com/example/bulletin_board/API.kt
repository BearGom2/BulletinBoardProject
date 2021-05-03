package com.example.bulletin_board

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @FormUrlEncoded
    @POST("/user/join")
    fun join(@Field("_id") id : String, @Field("_psw") pw : String, @Field("_name") name : String, @Field("_addr") email : String) : Call<Void>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("_id") id : String, @Field("_psw") pw : String) : Call<Void>
}