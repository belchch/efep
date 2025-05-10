package ru.bfe.efep.app.inspection.spot.room

import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Room, Long>