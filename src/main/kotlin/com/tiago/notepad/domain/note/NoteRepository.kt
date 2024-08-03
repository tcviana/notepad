package com.tiago.notepad.domain.note

import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

/**
 * Repositório para a entidade [Note].
 *
 * Este repositório fornece métodos CRUD padrão para a entidade [Note],
 * herdando da interface [JpaRepository] e [JpaSpecificationExecutor] para filtros
 *
 * @author tcviana
 * @since 2024-07-10
 */
@Profile("!local")
interface NoteRepository : CrudRepository<Note, Long>, JpaSpecificationExecutor<Note> {
}