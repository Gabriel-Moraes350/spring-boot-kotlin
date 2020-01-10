package com.example.eventskt.dtos

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.NotBlank

data class EventDTO(var id: Int = 0,
                    @field:NotBlank(message = "{validation.events.title}") var title: String?,
                    @field:NotBlank(message = "{validation.events.street}") var street: String = "",
                    @field:NotBlank(message = "{validation.events.neighbourhood}") var neighbourhood: String = "",
                    @field:NotNull(message = "{validation.events.number.notnull}")
                    @field:Min(message = "{validation.events.number.min}", value = 1) var number: Int = 0) {

}