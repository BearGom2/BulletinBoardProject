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
    ): Call<Void> //회원가입을 위한 주소

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("_id") id: String, @Field("_psw") pw: String): Call<userData> //로그인을 위한 주소

    @FormUrlEncoded
    @POST("/board/create")
    fun boardCreate(
        @Field("_title") title: String,
        @Field("_day") day: String,
        @Field("_content") content: String,
        @Field("_name") name: String,
        @Field("_id") id: String
    ): Call<Void> //게시판 생성을 위한 주소

    @FormUrlEncoded
    @POST("/user/login/check")
    fun check(@Field("_id") id: String): Call<Void> //ID 중복확인을 위한 주소

    @FormUrlEncoded
    @POST("/user/board/delete")
    fun delete(
        @Field("_title") title: String,
        @Field("_day") day: String,
        @Field("_content") content: String,
        @Field("_name") name: String,
        @Field("_id") id: String
    ): Call<Void> //게시판 삭제를 위한 주소

    @FormUrlEncoded
    @POST("/user/board/modify")
    fun modify(
        @Field("modify_title") modift_title: String,
        @Field("modify_day") modift_day: String,
        @Field("modify_content") modift_content: String,
        @Field("_title") title: String,
        @Field("_day") day: String,
        @Field("_content") content: String,
        @Field("_name") name: String,
        @Field("_id") id: String
    ): Call<Void> //게시판 수정을 위한 주소

    @POST("/board/list")
    fun boardList(): Call<ArrayList<boardData>> //게시판의 모든 것을 불러오기 위한 주소
}