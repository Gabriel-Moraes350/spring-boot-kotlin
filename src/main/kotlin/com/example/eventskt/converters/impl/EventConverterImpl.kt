package com.example.eventskt.converters.impl

import com.example.eventskt.converters.EventConverter
import com.example.eventskt.dtos.EventDTO
import com.example.eventskt.models.Address
import com.example.eventskt.models.Event
import org.springframework.beans.BeanUtils
import org.springframework.stereotype.Component

@Component
class EventConverterImpl: EventConverter {

    override fun convertDtoToModel(dto: EventDTO): Event{
        val event = Event()
        BeanUtils.copyProperties(dto, event)

        val address = Address()
        address.neighbourhood = dto.neighbourhood
        address.street = dto.street
        address.number = dto.number

        event.address = address

        return event
    }

    override fun convertModelToDTO(model: Event): EventDTO{
        val dto = EventDTO(title = model.title)
        BeanUtils.copyProperties(model, dto)

        if(model.address != null){
            dto.street = model.address!!.street.toString()
            dto.number = model.address!!.number!!
            dto.neighbourhood = model.address?.neighbourhood!!
        }

        return dto
    }

    override fun convertModelToDTO(model: Event, dto: EventDTO): EventDTO {
        val modelTemp = convertDtoToModel(dto)
        modelTemp.id = model.id

        return convertModelToDTO(modelTemp)
    }

}