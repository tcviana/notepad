package com.tiago.notepad

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class NotepadApplication

fun main(args: Array<String>) {
    runApplication<NotepadApplication>(*args)
}
