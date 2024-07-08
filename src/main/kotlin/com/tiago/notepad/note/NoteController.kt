package com.tiago.notepad.note

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("notes")
class NoteController {

    @GetMapping
    @ResponseBody
    fun list(): List<Note> {
        return listOf(
            Note("Leitura", "Livro de Spring Boot"),
            Note("Pesquisa", "Ambiente docker")
        )
    }
}