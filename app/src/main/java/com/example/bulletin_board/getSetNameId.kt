package com.example.bulletin_board

object getSetNameId {
    private var name: String = "guest"
    private var id: String = "guest"

    fun getId(): String {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getName(): String {
        return name
    }


    fun setName(name: String) {
        this.name = name
    }
}