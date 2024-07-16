package com.tiago.notepad.note

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

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

    /**
     * Deleta uma nota
     *
     * @param id - id da nota a ser removida
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNote(@PathVariable id: Long) {
        if (noteRepository.existsById(id))
            noteRepository.deleteById(id)
        else
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")
    }

    /**
     * Atualiza uma note
     *
     * @param id - id da nota a ser atualizada
     * @param note - nota a ser atualizada
     */
    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Long, @RequestBody note: Note): Note {
        return noteRepository.findById(id).map { existedNote ->
            val newNote = existedNote.copy(
                title = note.title,
                description = note.description
            )
            noteRepository.save(newNote)
        }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found") }
    }

}
