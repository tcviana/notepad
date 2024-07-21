package com.tiago.notepad

import com.fasterxml.jackson.databind.ObjectMapper
import com.tiago.notepad.domain.note.Note
import com.tiago.notepad.domain.note.NoteController
import com.tiago.notepad.domain.note.NoteRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(NoteController::class)
class NoteControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @MockBean
    lateinit var noteRepository: NoteRepository

    private val title = "Title"
    private val description = "Description"

    @Test
    fun `should return all notes`() {
        val notes = listOf(retrieveCreatedNote())
        `when`(noteRepository.findAll()).thenReturn(notes)

        mockMvc.perform(get("/notes"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$[0].title").value(title))
            .andExpect(jsonPath("\$[0].description").value(description))
    }

    @Test
    fun `should create a new note`() {
        val note = Note(title = this.title, description = this.description)
        val savedNote = note.copy(id = 1)

        `when`(noteRepository.save(note)).thenReturn(savedNote)

        mockMvc.perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value(title))
            .andExpect(jsonPath("$.description").value(description))
    }

    @Test
    fun `should delete a note`() {
        val noteId = 1L
        `when`(noteRepository.existsById(noteId)).thenReturn(true)

        mockMvc.perform(
            delete("/notes/{id}", noteId)
        ).andExpect(status().isNoContent)

        verify(noteRepository, times(1)).deleteById(noteId)
    }

    @Test
    fun `should return 404 when deleting a non-existing note`() {
        val noteId = 5L

        `when`(noteRepository.existsById(noteId)).thenReturn(false)

        mockMvc.perform(delete("/notes/{id}", noteId))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should update a note`() {
        val existedNote = retrieveCreatedNote()
        val title = "Update title"
        val description = "Update description"
        val updatedNote = existedNote.copy(title = title, description = description)

        `when`(noteRepository.findById(existedNote.id!!)).thenReturn(Optional.of(existedNote))
        `when`(noteRepository.save(updatedNote)).thenReturn(updatedNote)

        mockMvc.perform(
            put("/notes/{id}", existedNote.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNote))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(updatedNote.id))
            .andExpect(jsonPath("$.title").value(updatedNote.title))
            .andExpect(jsonPath("$.description").value(updatedNote.description))
    }

    @Test
    fun `should return 404 when update a non-existing note`() {
        val note = retrieveCreatedNote()

        `when`(noteRepository.findById(note.id!!)).thenReturn(Optional.empty())

        mockMvc.perform(
            put("/notes/{id}", note.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(note))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should partially update note`() {
        val existedNote = retrieveCreatedNote()
        val newNote = existedNote.copy(
            title = "Updated title"
        )

        `when`(noteRepository.findById(existedNote.id!!)).thenReturn(Optional.of(existedNote))
        `when`(noteRepository.save(newNote)).thenReturn(newNote)

        mockMvc.perform(
            patch("/notes/{id}", existedNote.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newNote))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(existedNote.id))
            .andExpect(jsonPath("$.title").value(newNote.title))
            .andExpect(jsonPath("$.description").value(existedNote.description))

        verify(noteRepository, times(1)).findById(existedNote.id!!)
        verify(noteRepository, times(1)).save(newNote)
    }

    private fun retrieveCreatedNote(): Note = Note(1, title, description)

}