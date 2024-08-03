package com.tiago.notepad.infrastructure

import com.tiago.notepad.domain.note.NoteRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.context.annotation.Profile

@EnableScan
@Profile("local")
interface NoteDynamoRepository : NoteRepository {
}