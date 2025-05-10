package ru.bfe.efep.app.inspection.material

import org.springframework.data.jpa.repository.JpaRepository

interface MaterialRepository : JpaRepository<Material, Long>