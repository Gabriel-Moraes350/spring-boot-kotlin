package com.example.eventskt.controllers

import com.example.eventskt.dtos.EventDTO
import com.example.eventskt.services.EventServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("events")
class EventsController {

    @Autowired
    private lateinit var eventServices: EventServices

    @GetMapping
    fun index(): List<EventDTO>{
        return eventServices.index()
    }

    @PostMapping
    fun insert(@Valid @RequestBody dto: EventDTO): EventDTO{
        return eventServices.save(dto)
    }

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody dto: EventDTO, @PathVariable(name= "id") id: Int): EventDTO{
        val event = eventServices.findEventById(id)
        return eventServices.update(event, dto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable(name= "id") id: Int){
        eventServices.delete(id)
    }

}