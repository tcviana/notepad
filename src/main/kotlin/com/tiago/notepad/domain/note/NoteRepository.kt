package com.tiago.notepad.domain.note

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/**
 * Repositório para a entidade [Note].
 *
 * Este repositório fornece métodos CRUD padrão para a entidade [Note],
 * herdando da interface [JpaRepository] e [JpaSpecificationExecutor] para filtros
 *
 * @author tcviana
 * @since 2024-07-10
 */
interface NoteRepository : JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
}