package com.tiago.notepad.domain.note

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteService(private val noteRepository: NoteRepository) {

    @CachePut(value = ["notes"], key = "#result.id")
    fun createNote(note: Note): Note {
        return noteRepository.save(note)
    }

    @Cacheable(value = ["notes"])
    fun getNotes(): MutableIterable<Note> {
        println("Get list")
        return noteRepository.findAll()
    }

    @CachePut(value = ["notes"], key = "#id")
    fun updateNote(id: Long, updateFields: Map<String, Any>): Note? {
        return noteRepository.findById(id).map { existedNote ->
            val newNote = existedNote.copy(
                title = updateFields["title"] as? String ?: existedNote.title,
                description = updateFields["description"] as? String ?: existedNote.description
            )
            noteRepository.save(newNote)
        }.orElse(null)
    }

    @CacheEvict(value = ["notes"], key = "#id")
    fun deleteNote(id: Long): Boolean {
        println("Get no $id")
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id)
            return true
        }
        return false
    }

    @Cacheable(value = ["notes"], key = "#id")
    fun getNoteById(id: Long): Optional<Note> {
        println("Get by id")
        return noteRepository.findById(id)
    }

    @Cacheable(value = ["notes"])
    fun findNotes(note: Note): List<Note> {
        val specification = NoteSpecification.specification(note)
        return noteRepository.findAll(specification)
    }
}