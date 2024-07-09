package com.tiago.notepad.note

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("notes")
class NoteController {

    @GetMapping
    fun list(): List<Note> {
        return listOf(
            Note("Leitura", "Livro de Spring Boot"),
            Note("Pesquisa", "Ambiente docker")
        )
    }

    @PostMapping
    fun add(@RequestBody note: Note): Note {
        return note
    }
}