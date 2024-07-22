package com.tiago.notepad

import com.fasterxml.jackson.databind.ObjectMapper
import com.tiago.notepad.domain.note.Note
import com.tiago.notepad.domain.note.NoteController
import com.tiago.notepad.domain.note.NoteService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(NoteController::class)
class NoteControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @MockBean
    lateinit var noteService: NoteService

    private val title = "Title"
    private val description = "Description"

    @Test
    fun `should return all notes`() {
        val notes = listOf(retrieveCreatedNote())
        `when`(noteService.getNotes()).thenReturn(notes)

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

        `when`(noteService.createNote(note)).thenReturn(savedNote)

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
        `when`(noteService.deleteNote(noteId)).thenReturn(true)

        mockMvc.perform(
            delete("/notes/{id}", noteId)
        ).andExpect(status().isNoContent)

        verify(noteService, times(1)).deleteNote(noteId)
    }

    @Test
    fun `should return 404 when deleting a non-existing note`() {
        val noteId = 5L

        `when`(noteService.deleteNote(noteId)).thenReturn(false)

        mockMvc.perform(delete("/notes/{id}", noteId))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should update a note`() {
        val existedNote = retrieveCreatedNote()
        val title = "Update title"
        val description = "Update description"
        val updatedNote = existedNote.copy(title = title, description = description)
        val updateFields: Map<String, Any> = createUpdateFields(updatedNote)

        `when`(noteService.updateNote(existedNote.id!!, updateFields)).thenReturn(updatedNote)

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
        val updateFields = createUpdateFields(note)

        `when`(noteService.updateNote(note.id!!, updateFields)).thenReturn(null)

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
        val updateFields = createUpdateFields(newNote)

        `when`(noteService.updateNote(existedNote.id!!, updateFields)).thenReturn(newNote)

        mockMvc.perform(
            patch("/notes/{id}", existedNote.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFields))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(existedNote.id))
            .andExpect(jsonPath("$.title").value(newNote.title))
            .andExpect(jsonPath("$.description").value(existedNote.description))

        verify(noteService, times(1)).updateNote(existedNote.id!!, updateFields)
    }

    private fun retrieveCreatedNote(): Note = Note(1, title, description)

    private fun createUpdateFields(updatedNote: Note): Map<String, Any> {
        return mapOf(
            "title" to updatedNote.title,
            "description" to updatedNote.description
        )
    }
}
