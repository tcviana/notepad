package com.tiago.notepad.note

import org.springframework.web.bind.annotation.*

/**
 * A classe [NoteController] representa o controlador REST para gerenciar notas.
 *
 * @see [Note]
 * @author tcviana
 * @since 2024-07-10
 */

@RestController
@RequestMapping("notes")
class NoteController {

    /**
     * Retorna todas as notas.
     *
     * @return Lista de todas as notas
     */
    @GetMapping
    fun getAllNotes(): List<Note> {
        return listOf(
            Note("Leitura", "Livro de Spring Boot"),
            Note("Pesquisa", "Ambiente docker")
        )
    }

    /**
     * Cria uma nova nota.
     *
     * @param note A nota a ser criada.
     * @return A nota criada.
     */
    @PostMapping
    fun createNote(@RequestBody note: Note): Note {
        return note
    }
}