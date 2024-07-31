package com.tiago.notepad.infrastructure

import com.tiago.notepad.domain.note.Note
import com.tiago.notepad.domain.note.NoteRepository
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

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
@Profile("local")
interface NoteH2Repository : NoteRepository {
}