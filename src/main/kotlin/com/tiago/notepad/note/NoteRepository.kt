package com.tiago.notepad.note

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repositório para a entidade [Note].
 *
 * Este repositório fornece métodos CRUD padrão para a entidade [Note],
 * herdando da interface [JpaRepository].
 *
 * @author tcviana
 * @since 2024-07-10
 */
interface NoteRepository : JpaRepository<Note, Long> {
}