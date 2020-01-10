package com.example.eventskt.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EventsTest {

    @Test
    fun shouldCreateSlugCorrectly(){
        val event = Event()
        event.title = "teste 123"
        event.generateSlug()

        val expectedSlug = "teste_123"
        assertEquals(expectedSlug, event.slug)
    }

    @Test
    fun shouldKeepSlugEmpty(){
        val event = Event()
        event.generateSlug()

        val expectedSlug = ""
        assertEquals(expectedSlug, event.slug)
    }
}