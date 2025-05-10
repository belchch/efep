package ru.bfe.efep

import jakarta.transaction.Transactional
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.bfe.efep.app.inspection.spot.room.Room
import ru.bfe.efep.app.inspection.spot.room.RoomRepository
import ru.bfe.efep.app.inspection.spot.Spot
import ru.bfe.efep.app.inspection.spot.SpotRepository

@SpringBootApplication
class EfepApplication(
    /*val courtRepository: CourtRepository,
    val caseRepository: CaseRepository*/

    val spotRepository: SpotRepository,
    val roomRepository: RoomRepository
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments?) {
        /*val (court, cases) = generateSampleData()

        courtRepository.save(court)
        caseRepository.saveAll(cases)*/

        //spotsAndRooms()

    }

    fun spotsAndRooms() {
        // Очищаем данные (опционально)
        roomRepository.deleteAll()
        spotRepository.deleteAll()

        // Создаем обычный Spot
        val spot = Spot(name = "General Parking Spot")
        spotRepository.save(spot)

        // Создаем Room (который наследуется от Spot)
        val room = Room(name = "Conference Room A")
        roomRepository.save(room)

        // Проверяем данные
        println("All Spots:")
        spotRepository.findAll().forEach { println(it.name) }

        println("\nAll Rooms:")
        roomRepository.findAll().forEach {
            println("${it.name} ")
        }
    }

    /*fun generateSampleData(): Pair<Court, List<Case>> {
        // Создаем суд
        val court = Court(
            number = "COURT-001",
            name = "Верховный суд примера",
            postalCode = "123456",
            region = "Примерный регион"
        )

        // Создаем два дела, связанные с этим судом
        val case1 = Case(
            number = "CASE-2023-001",
            status = CaseStatus.OPEN,
            priority = CasePriority.HIGH,
            facilityAddress = "ул. Примерная, 123, г. Примерный",
            court = court
        )

        val case2 = Case(
            number = "CASE-2023-002",
            status = CaseStatus.IN_PROGRESS,
            priority = CasePriority.MEDIUM,
            facilityAddress = "пр. Образцовый, 456, г. Тестовый",
            court = court
        )

        return court to listOf(case1, case2)
    }*/

}

fun main(args: Array<String>) {
    runApplication<EfepApplication>(*args)
}
