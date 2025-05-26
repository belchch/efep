package ru.bfe.efep.app.defect

import org.springframework.data.jpa.repository.JpaRepository

interface DefectRepository : JpaRepository<Defect, Long>