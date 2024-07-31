package com.tiago.notepad.infrastructure

import com.tiago.notepad.domain.note.Note
import com.tiago.notepad.domain.note.NoteRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

/**
 * Repositório para a entidade [Note].
 *
 * Este repositório fornece métodos CRUD padrão para a entidade [Note],
 * herdando da interface [JpaRepository] e [JpaSpecificationExecutor] para filtros
 *
 * @author tcviana
 * @since 2024-07-29
 */
@Repository
@Profile("docker")
abstract class NoteDynamoRepository(
    private val dynamoDbClient: DynamoDbClient
) : NoteRepository {

    private val enhancedClient: DynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
        .dynamoDbClient(dynamoDbClient)
        .build()

    private val table: DynamoDbTable<Note> = enhancedClient.table("NotesTable", TableSchema.fromBean(Note::class.java))

    override fun <S : Note?> save(entity: S & Any): S & Any {
        table.putItem(entity)
        return entity
    }

}