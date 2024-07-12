package com.tiago.notepad

import com.tiago.notepad.note.Note
import com.tiago.notepad.note.NoteController
import com.tiago.notepad.note.NoteRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(NoteController::class)
class NoteControllerTests @Autowired constructor(
    val mockMvc: MockMvc
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

    private fun retrieveCreatedNote(): Note = Note(1, title, description)

}