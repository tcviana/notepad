package com.tiago.notepad

import com.fasterxml.jackson.databind.ObjectMapper
import com.tiago.notepad.note.Note
import com.tiago.notepad.note.NoteController
import com.tiago.notepad.note.NoteRepository
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
        doNothing().`when`(noteRepository).deleteById(noteId)

        mockMvc.perform(
            delete("/notes/{id}", noteId)
        ).andExpect(status().isNoContent)

        verify(noteRepository, times(1)).deleteById(noteId)
    }

    private fun retrieveCreatedNote(): Note = Note(1, title, description)

}