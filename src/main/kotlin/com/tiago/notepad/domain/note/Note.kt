package com.tiago.notepad.domain.note

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable

/**
 * A classe [Note] representa uma nota no sistema.
 *
 * @property id Identificador único da nota.
 * @property title Título da nota.
 * @property description Descrição da nota.
 *
 * @author tcviana
 * @since 2024-07-10
 */

@Entity
data class Note(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val title: String = "",
    val description: String = ""
) : Serializable