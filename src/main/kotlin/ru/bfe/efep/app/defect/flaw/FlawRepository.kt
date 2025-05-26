package ru.bfe.efep.app.defect.flaw

import org.springframework.data.jpa.repository.JpaRepository

interface FlawRepository : JpaRepository<Flaw, Long>