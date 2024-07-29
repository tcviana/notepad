package com.tiago.notepad.domain.note

import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

object NoteSpecification {

    fun specification(note: Note): Specification<Note> {
        return Specification { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            predicates.add(criteriaBuilder.like(root.get("title"), retrieveValueWithWildCard(note.title)))
            predicates.add(criteriaBuilder.like(root.get("description"), retrieveValueWithWildCard(note.description)))
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

    private fun retrieveValueWithWildCard(value: String): String {
        val wildCard = "%"
        return wildCard + value + wildCard
    }
}