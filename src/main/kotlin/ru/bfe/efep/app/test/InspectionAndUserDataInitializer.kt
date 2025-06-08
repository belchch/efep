package ru.bfe.efep.app.test

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ru.bfe.efep.app.cases.CaseRepository
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.user.User
import ru.bfe.efep.app.user.UserRepository
import java.time.Instant


class InspectionAndUserDataInitializer(
    private val userRepository: UserRepository,
    private val inspectionRepository: InspectionRepository,
    private val casesRepository: CaseRepository,
) {

    fun run(vararg args: String?) {
        // Создаем пользователя
        val user = User(
            username = "inspector1",
            password = "securepassword123"
        )
        val savedUser = userRepository.save(user)
        println("Создан пользователь: ${savedUser.username} с ID ${savedUser.id}")

        // Создаем первую инспекцию
        val inspection1 = Inspection(
            address = "ул. Примерная, д. 10, кв. 5",
            performedDate = Instant.now(),
            performedBy = savedUser,
            case = casesRepository.findById(1).orElse(null),
        )
        val savedInspection1 = inspectionRepository.save(inspection1)
        println("Создана инспекция 1: ${savedInspection1.address}")

        // Создаем вторую инспекцию
        val inspection2 = Inspection(
            address = "пр. Тестовый, д. 42, офис 3",
            performedDate = Instant.now().minusSeconds(86400), // вчера
            performedBy = savedUser,
            case = casesRepository.findById(2).orElse(null),
        )
        val savedInspection2 = inspectionRepository.save(inspection2)
        println("Создана инспекция 2: ${savedInspection2.address}")

    }
}