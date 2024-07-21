package com.tiago.notepad.domain.note

/**
 * A classe [NoteDTO] é responsável pelo tratamento dos campos da entidade [Note]
 *
 * @property id Identificador único da nota.
 * @property title Título da nota.
 * @property description Descrição da nota.
 *
 * @author tcviana
 * @since 2024-07-19
 */

data class NoteDTO(
    val id: Long? = null,
    val title: String,
    val description: String
)