package ru.bfe.efep.app.court

import org.springframework.data.jpa.repository.JpaRepository

interface CourtRepository : JpaRepository<Court, Long>