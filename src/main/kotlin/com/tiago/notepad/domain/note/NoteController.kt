package com.tiago.notepad.domain.note

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
class NoteController(private val noteService: NoteService) {

    /**
     * Retorna todas as notas.
     *
     * @return Lista de todas as notas
     */
    @GetMapping
    fun getAllNotes(): List<NoteDTO> = noteService.getNotes().map { NoteMapper.toDTO(it) }

    /**
     * Cria uma nova nota.
     *
     * @param note A nota a ser criada.
     * @return A nota criada.
     */
    @PostMapping
    fun createNote(@RequestBody noteDTO: NoteDTO): NoteDTO {
        val note = NoteMapper.toEntity(noteDTO)
        return NoteMapper.toDTO(noteService.createNote(note))
    }

    /**
     * Deleta uma nota
     *
     * @param id - id da nota a ser removida
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNote(@PathVariable id: Long) {
        if (!noteService.deleteNote(id))
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")
    }

    /**
     * Atualiza uma note
     *
     * @param id - id da nota a ser atualizada
     * @param noteDTO - nota a ser atualizada
     * @return A nota atualizada.
     */
    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Long, @RequestBody note: NoteDTO): ResponseEntity<NoteDTO> {
        return partiallyUpdateNote(
            id, mapOf(
                "title" to note.title,
                "description" to note.description
            )
        )
    }

    /**
     * Atualiza parcialmente uma nota.
     *
     * @param id - id da nota a ser atualizada
     * @param updateFields - campos a serem atualizados
     * @return A nota atualizada.
     */
    @PatchMapping("/{id}")
    fun partiallyUpdateNote(
        @PathVariable id: Long,
        @RequestBody updateFields: Map<String, Any>
    ): ResponseEntity<NoteDTO> {
        val note = noteService.updateNote(id, updateFields)
        return if (note != null) {
            ResponseEntity(NoteMapper.toDTO(note), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Retorna uma nota pelo ID.
     *
     * @param id - id da nota a ser retornada
     * @return A nota encontrada ou um status 404 se não for encontrada
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<NoteDTO> {
        val note = noteService.getNoteById(id)
        return if (note.isPresent) {
            ResponseEntity(NoteMapper.toDTO(note.get()), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    /**
     * Retorna uma lista de notas com base nos filtros
     *
     * @param note specification com os campos a serem filtrados
     * return lista de notas encontradas
     */
    @GetMapping("/search")
    fun findNotes(@RequestBody note: NoteDTO): List<NoteDTO> {
        return noteService.findNotes(NoteMapper.toEntity(note)).map { NoteMapper.toDTO(it) }
    }

}
