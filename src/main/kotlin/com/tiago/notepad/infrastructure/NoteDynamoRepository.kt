package com.tiago.notepad.infrastructure

import com.tiago.notepad.domain.note.NoteRepository
import org.springframework.context.annotation.Profile

@Profile("docker")
interface NoteDynamoRepository : NoteRepository {
}