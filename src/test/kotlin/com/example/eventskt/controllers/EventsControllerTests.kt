package com.example.eventskt.controllers

import com.example.eventskt.dtos.EventDTO
import com.example.eventskt.exceptions.EventNotFoundException
import com.example.eventskt.services.EventServices
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(EventsController::class)
class EventsControllerTests {

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var eventServices: EventServices

    private val eventExpectedToBeSaved = EventDTO(title = "teste", street = "teste", neighbourhood = "teste", number = 123)

    private val ROUTE_PREFIX = "/events"

    @Test
    fun getShouldReturnOK(){
        mockMvc.perform(get(ROUTE_PREFIX)).andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        verify(eventServices).index()
    }

    @Test
    fun getShouldReturnList(){
        val eventsDTOs = listOf(EventDTO(title = "teste"))
        `when`(eventServices.index()).thenReturn(eventsDTOs)

        val mvcResult = mockMvc.perform(get(ROUTE_PREFIX)).andReturn()

        val bodyResponse = mvcResult.response.contentAsString
        val expectedBodyResponse = mapper.writeValueAsString(eventsDTOs)

        verify(eventServices).index()
        assertEquals(expectedBodyResponse, bodyResponse)
    }

    @Test
    fun shouldReturnStatus422(){

        val requestInJson = mapper.writeValueAsString(EventDTO(title = ""))
        mockMvc.perform(post(ROUTE_PREFIX)
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun postShouldReturnOK(){

        `when`(eventServices.save(eventExpectedToBeSaved)).thenReturn(eventExpectedToBeSaved)
        val requestInJson = mapper.writeValueAsString(eventExpectedToBeSaved)


        mockMvc.perform(post(ROUTE_PREFIX)
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        verify(eventServices).save(eventExpectedToBeSaved)
    }

    @Test
    fun shouldReturnStatus422OnUpdate(){

        val requestInJson = mapper.writeValueAsString(EventDTO(title = ""))
        mockMvc.perform(put("$ROUTE_PREFIX/1")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun shouldReturnStatus404IfNotFoundEvent(){

        `when`(eventServices.findEventById(1)).thenThrow(EventNotFoundException("event.not-found"))

        val requestInJson = mapper.writeValueAsString(eventExpectedToBeSaved)
        mockMvc.perform(put("$ROUTE_PREFIX/1")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound)

        verify(eventServices).findEventById(1)
    }

    @Test
    fun deleteShouldReturnOK(){
        mockMvc.perform(delete("$ROUTE_PREFIX/2")).andDo(print())
                .andExpect(status().isOk)

        verify(eventServices).delete(2)
    }
}