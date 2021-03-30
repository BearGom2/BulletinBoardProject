package com.example.bulletin_board

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class boardData (
    val title : String,
    val writer : String,
    val day : String,
    val content : String
) : Serializable