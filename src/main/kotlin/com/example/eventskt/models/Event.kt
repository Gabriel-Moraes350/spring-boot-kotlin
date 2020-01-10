package com.example.eventskt.models

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull

@Table(name = "events")
@Entity
class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null

    var slug: String? = null

    @Column(nullable = false)
    var title: String? = null
    var address: Address? = null

    @CreatedDate
    var createdAt: LocalDate? = null

    fun generateSlug(){
        this.slug = title?.replace(" ", "_")
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }

    override fun toString(): String {
        return "Event(id=$id, slug=$slug, title=$title, address=$address, createdAt=$createdAt)"
    }


}