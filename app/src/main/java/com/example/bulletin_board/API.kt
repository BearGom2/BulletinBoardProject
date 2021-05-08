package com.example.bulletin_board

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {
    @FormUrlEncoded
    @POST("/user/join")
    fun join(
        @Field("_id") id: String,
        @Field("_psw") pw: String,
        @Field("_name") name: String
    ): Call<Void>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("_id") id: String, @Field("_psw") pw: String): Call<userData>

    @FormUrlEncoded
    @POST("/board/create")
    fun board_create(
        @Field("_title") title: String,
        @Field("_day") day: String,
        @Field("_content") content: String,
        @Field("_name") name: String
    ): Call<Void>

    @FormUrlEncoded
    @POST("/user/login/check")
    fun check(@Field("_id") id: String): Call<Void>

    @FormUrlEncoded
    @POST("/user/board/delete")
    fun delete(
        @Field("_title") title: String,
        @Field("_day") day: String,
        @Field("_content") content: String,
        @Field("_name") name: String
    ): Call<Void>

    @POST("/board/list")
    fun board_list(): Call<ArrayList<boardData>>
}