package ru.bfe.efep

import jakarta.transaction.Transactional
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.bfe.efep.app.cases.Case
import ru.bfe.efep.app.cases.CasePriority
import ru.bfe.efep.app.cases.CaseRepository
import ru.bfe.efep.app.cases.CaseStatus
import ru.bfe.efep.app.court.Court
import ru.bfe.efep.app.court.CourtRepository

@SpringBootApplication
class EfepApplication(
    val courtRepository: CourtRepository,
    val caseRepository: CaseRepository
) : ApplicationRunner {
    @Transactional
    override fun run(args: ApplicationArguments?) {
        /*val (court, cases) = generateSampleData()

        courtRepository.save(court)
        caseRepository.saveAll(cases)*/

        println(courtRepository.findAll())
        println(caseRepository.findAll())
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
