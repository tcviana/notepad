package com.tiago.notepad.note

import org.springframework.beans.factory.annotation.Autowired
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
class NoteController(@Autowired private val noteRepository: NoteRepository) {

    /**
     * Retorna todas as notas.
     *
     * @return Lista de todas as notas
     */
    @GetMapping
    fun getAllNotes(): List<Note> = noteRepository.findAll()

    /**
     * Cria uma nova nota.
     *
     * @param note A nota a ser criada.
     * @return A nota criada.
     */
    @PostMapping
    fun createNote(@RequestBody note: Note): Note = noteRepository.save(note)
}