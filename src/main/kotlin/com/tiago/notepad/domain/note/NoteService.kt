package com.tiago.notepad.domain.note

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class NoteService(@Autowired val redisTemplate: RedisTemplate<String, Any>) {

    fun saveNoteToRedis(note: Note) {
        redisTemplate.opsForValue().set(note.id.toString(), note)
    }

    @Cacheable(value = ["notes"], key = "#id")
    fun getNoteFromRedis(id: Long): Note? {
        return redisTemplate.opsForValue().get(id.toString()) as Note?
    }
}