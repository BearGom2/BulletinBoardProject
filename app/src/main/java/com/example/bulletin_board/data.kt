package com.example.bulletin_board

data class boardData(
    var _title: String,
    var _name: String,
    var _day: String,
    var _content: String,
    var _id: String
) //게시판 data를 받아오기 위한 data class

data class userData(
    val id: String,
    val name: String
)//사용자 data를 받아오기 위한 data class