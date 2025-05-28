package ru.bfe.efep.app.defect

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface DefectRepository : JpaRepository<Defect, Long>, JpaSpecificationExecutor<Defect> {

}