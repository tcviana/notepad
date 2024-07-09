package com.tiago.notepad.note

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Note(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val title: String = "",
    val description: String = ""
)