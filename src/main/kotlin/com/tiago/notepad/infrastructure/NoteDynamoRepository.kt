package com.tiago.notepad.infrastructure

import com.tiago.notepad.domain.note.Note
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface NoteDynamoRepository : CrudRepository<Note, String> {
}