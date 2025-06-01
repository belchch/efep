package ru.bfe.efep.app.inspection.tr

import org.springframework.data.jpa.repository.JpaRepository

interface TechnicalReportRepository : JpaRepository<TechnicalReport, Long>