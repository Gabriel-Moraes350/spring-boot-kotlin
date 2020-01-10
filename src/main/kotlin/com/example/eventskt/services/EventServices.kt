package com.example.eventskt.services

import com.example.eventskt.dtos.EventDTO
import com.example.eventskt.models.Event

interface EventServices {
    fun findEventById(id: Int): Event
    fun update(event: Event, bodyDto: EventDTO): EventDTO
    fun save(eventDTO: EventDTO) : EventDTO
    fun index(): List<EventDTO>
    fun delete(id: Int)
}