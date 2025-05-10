package ru.bfe.efep.app.inspection.spot.room

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val roomService: RoomService
) {

    @PostMapping
    fun createRoom(@RequestBody request: RoomUpdateRequest): ResponseEntity<RoomResponse> {
        val createdRoom = roomService.createRoom(request)
        return ResponseEntity(createdRoom, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getRoom(@PathVariable id: Long): ResponseEntity<RoomResponse> {
        val court = roomService.getRoom(id)
        return ResponseEntity.ok(court)
    }

    @GetMapping
    fun getAllRooms(): ResponseEntity<List<RoomResponse>> {
        val courts = roomService.getAllRooms()
        return ResponseEntity.ok(courts)
    }

    @PutMapping("/{id}")
    fun updateRoom(
        @PathVariable id: Long,
        @RequestBody request: RoomUpdateRequest
    ): ResponseEntity<RoomResponse> {
        val updatedRoom = roomService.updateRoom(id, request)
        return ResponseEntity.ok(updatedRoom)
    }

    @DeleteMapping("/{id}")
    fun deleteRoom(@PathVariable id: Long): ResponseEntity<Void> {
        roomService.deleteRoom(id)
        return ResponseEntity.noContent().build()
    }
}