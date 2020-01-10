package com.example.eventskt.services.impl

import com.example.eventskt.converters.EventConverter
import com.example.eventskt.dtos.EventDTO
import com.example.eventskt.exceptions.EventNotFoundException
import com.example.eventskt.models.Event
import com.example.eventskt.repositories.EventRepository
import com.example.eventskt.services.EventServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventServicesImpl : EventServices {

    @Autowired
    private lateinit var eventRepository: EventRepository

    @Autowired
    private lateinit var eventConverter: EventConverter

    override fun findEventById(id: Int): Event {
        return eventRepository.findById(id).orElseThrow { EventNotFoundException("event.not-found") }
    }

    override fun update(event: Event, bodyDto: EventDTO): EventDTO {
        val dto = eventConverter.convertModelToDTO(event, bodyDto)

        return save(dto)
    }

    override fun save(eventDTO: EventDTO): EventDTO {
        var event: Event = eventConverter.convertDtoToModel(eventDTO)
        event.generateSlug()
        event = eventRepository.save(event)

        return eventConverter.convertModelToDTO(event)
    }

    override fun index(): List<EventDTO> {
        val events = eventRepository.findAll()

        return events.map { eventConverter.convertModelToDTO(it) }
    }

    override fun delete(id: Int) {
        val event = findEventById(id)
        eventRepository.delete(event)
    }


}