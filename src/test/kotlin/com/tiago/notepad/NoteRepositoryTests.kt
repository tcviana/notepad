package com.tiago.notepad

import com.tiago.notepad.note.Note
import com.tiago.notepad.note.NoteRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJpaTest
class NoteRepositoryTests @Autowired constructor(val noteRepository: NoteRepository) {

    @Test
    fun `should save and find note`() {
        val title = "Title test"
        val description = "Description test"
        val note = Note(title = title, description = description)
        val savedNote = noteRepository.save(note)

        val foundNote = noteRepository.findById(savedNote.id!!).orElse(null)
        assertNotNull(foundNote)
        assertEquals(title, foundNote.title)
        assertEquals(description, foundNote.description)
    }
}