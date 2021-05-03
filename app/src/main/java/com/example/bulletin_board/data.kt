package com.example.bulletin_board

import java.io.Serializable

data class boardData(
    val title: String,
    val writer: String,
    val day: String,
    val content: String
) : Serializable

data class userData(
    val _id: String,
    val _psw: String,
    val _name: String,
    val _email: String
) : Serializable