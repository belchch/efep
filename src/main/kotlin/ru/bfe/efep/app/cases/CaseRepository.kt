package ru.bfe.efep.app.cases

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CaseRepository : JpaRepository<Case, Long>, JpaSpecificationExecutor<Case>