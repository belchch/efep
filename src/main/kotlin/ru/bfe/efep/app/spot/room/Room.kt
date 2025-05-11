package ru.bfe.efep.app.spot.room

import jakarta.persistence.Entity
import jakarta.persistence.Table
import ru.bfe.efep.app.spot.Spot

@Entity
@Table(name = "rooms")
class Room : Spot {
    constructor(id: Long? = null, name: String) : super(id = id, name = name)
}