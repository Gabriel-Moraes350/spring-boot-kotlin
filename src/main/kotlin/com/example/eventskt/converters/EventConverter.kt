package com.example.eventskt.converters

import com.example.eventskt.dtos.EventDTO
import com.example.eventskt.models.Event

interface EventConverter {
    fun convertDtoToModel(dto: EventDTO): Event
    fun convertModelToDTO(model: Event): EventDTO
    fun convertModelToDTO(model: Event, dto: EventDTO): EventDTO
}