package com.tiago.notepad.note

/**
 * A classe [NoteMapper] é um singleton responsável pela conversão de um objeto
 * do tipo [Note] para [NoteDTO] e vice-versa
 */
object NoteMapper {
    fun toEntity(noteDTO: NoteDTO): Note {
        return Note(
            id = noteDTO.id,
            title = noteDTO.title,
            description = noteDTO.description
        )
    }

    fun toDTO(note: Note): NoteDTO {
        return NoteDTO(
            id = note.id,
            title = note.title,
            description = note.description
        )
    }
}