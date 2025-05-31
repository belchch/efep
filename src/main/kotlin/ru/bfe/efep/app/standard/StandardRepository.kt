package ru.bfe.efep.app.standard

import org.springframework.data.jpa.repository.JpaRepository

interface StandardRepository : JpaRepository<Standard, Long> {}