package ru.bfe.efep.app.cases

import org.springframework.data.jpa.repository.JpaRepository

interface CaseRepository : JpaRepository<Case, Long>