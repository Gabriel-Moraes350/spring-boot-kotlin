package com.example.eventskt.repositories

import com.example.eventskt.models.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Int> {
}