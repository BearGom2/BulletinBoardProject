package com.example.bulletin_board

data class boardData(
    var _title: String,
    var _name: String,
    var _day: String,
    var _content: String
)

data class userData(
    val id: String,
    val name: String
)