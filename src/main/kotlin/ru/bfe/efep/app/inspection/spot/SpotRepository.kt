package ru.bfe.efep.app.inspection.spot

import org.springframework.data.jpa.repository.JpaRepository

interface SpotRepository : JpaRepository<Spot, Long>