package com.example.bulletin_board

object getSetName {
    private var name: String = "default"
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }
}