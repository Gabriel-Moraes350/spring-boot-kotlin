package com.example.eventskt.models

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Address {

    @Column(length = 500)
    var street: String? = null

    var number: Int? = null
    var neighbourhood: String? = null


    override fun toString(): String {
        return "Address(street=$street, number=$number, neighbourhood=$neighbourhood)"
    }
}
