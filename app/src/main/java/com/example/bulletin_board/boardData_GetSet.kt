package com.example.bulletin_board

var boardList: ArrayList<boardData> = arrayListOf()

class boardData_GetSet {
    fun boardSet(data: boardData) {
        boardList.add(data)
    }

    fun boardGet(): ArrayList<boardData> {
        return boardList
    }
}