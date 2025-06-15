package ru.bfe.efep.app.test

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.bfe.efep.app.cases.Case
import ru.bfe.efep.app.cases.CasePriority
import ru.bfe.efep.app.cases.CaseRepository
import ru.bfe.efep.app.cases.CaseStatus
import ru.bfe.efep.app.company.Company
import ru.bfe.efep.app.company.CompanyRepository
import ru.bfe.efep.app.court.Court
import ru.bfe.efep.app.court.CourtRepository
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.judge.Judge
import ru.bfe.efep.app.judge.JudgeRepository
import ru.bfe.efep.app.region.Region
import ru.bfe.efep.app.region.RegionRepository
import ru.bfe.efep.app.user.User
import ru.bfe.efep.app.user.UserRepository
import java.time.Instant
import kotlin.random.Random


class CaseDataInitializer(
    private val courtRepository: CourtRepository,
    private val judgeRepository: JudgeRepository,
    private val companyRepository: CompanyRepository,
    private val regionRepository: RegionRepository,
    private val userRepository: UserRepository,
    private val caseRepository: CaseRepository,
    private val inspectionRepository: InspectionRepository
    //private val inspectionAndUserDataInitializer: InspectionAndUserDataInitializer
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        // Генерация регионов
        val regions = listOf(
            Region(code = "RU-MOW", name = "Москва"),
            Region(code = "RU-SPE", name = "Санкт-Петербург"),
            Region(code = "RU-NVS", name = "Новосибирская область"),
            Region(code = "RU-SVE", name = "Свердловская область"),
            Region(code = "RU-KDA", name = "Краснодарский край"),
            Region(code = "RU-TAT", name = "Татарстан"),
            Region(code = "RU-CHE", name = "Челябинская область"),
            Region(code = "RU-ROS", name = "Ростовская область"),
            Region(code = "RU-PER", name = "Пермский край"),
            Region(code = "RU-KR", name = "Красноярский край")
        )
        regionRepository.saveAll(regions)

        // Генерация компаний
        val companies = listOf(
            Company(name = "ООО \"Ромашка\"", inn = "7701234567"),
            Company(name = "АО \"ТехноПром\"", inn = "7712345678"),
            Company(name = "ПАО \"Сбербанк\"", inn = "7734567890"),
            Company(name = "ООО \"Газпром Нефть\"", inn = "7845678901"),
            Company(name = "ООО \"Яндекс\"", inn = "7736512345"),
            Company(name = "АО \"МТС\"", inn = "7744012345"),
            Company(name = "ПАО \"ВТБ\"", inn = "7725012345"),
            Company(name = "ООО \"МегаФон\"", inn = "7743012345"),
            Company(name = "АО \"РЖД\"", inn = "7708501234"),
            Company(name = "ООО \"Лукойл\"", inn = "7706012345")
        )
        companyRepository.saveAll(companies)

        // Генерация судов
        val courts = listOf(
            Court(name = "Арбитражный суд города Москвы", postalCode = "115225", region = "Москва"),
            Court(name = "Арбитражный суд Московской области", postalCode = "143402", region = "Московская область"),
            Court(name = "Арбитражный суд Санкт-Петербурга и Ленинградской области", postalCode = "190000", region = "Санкт-Петербург"),
            Court(name = "Арбитражный суд Свердловской области", postalCode = "620075", region = "Свердловская область"),
            Court(name = "Арбитражный суд Новосибирской области", postalCode = "630102", region = "Новосибирская область"),
            Court(name = "Арбитражный суд Республики Татарстан", postalCode = "420111", region = "Татарстан"),
            Court(name = "Арбитражный суд Краснодарского края", postalCode = "350063", region = "Краснодарский край"),
            Court(name = "Арбитражный суд Челябинской области", postalCode = "454091", region = "Челябинская область"),
            Court(name = "Арбитражный суд Ростовской области", postalCode = "344019", region = "Ростовская область"),
            Court(name = "Арбитражный суд Пермского края", postalCode = "614990", region = "Пермский край")
        )
        courtRepository.saveAll(courts)

        // Генерация судей
        val judges = mutableListOf<Judge>()
        courts.forEach { court ->
            repeat(2) { i ->
                judges.add(
                    Judge(
                        court = court,
                        firstName = listOf("Иван", "Петр", "Сергей", "Алексей", "Дмитрий").random(),
                        middleName = listOf("Иванович", "Петрович", "Сергеевич", "Алексеевич", "Дмитриевич").random(),
                        lastName = listOf("Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов").random()
                    )
                )
            }
        }
        judgeRepository.saveAll(judges)

        // Генерация пользователей
        val users = listOf(
            User(username = "admin", password = "\$2a\$10\$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu"), // password: admin
            User(username = "user1", password = "\$2a\$10\$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu"), // password: admin
            User(username = "user2", password = "\$2a\$10\$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu"), // password: admin
            User(username = "user3", password = "\$2a\$10\$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu"), // password: admin
            User(username = "user4", password = "\$2a\$10\$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu"), // password: admin
            User(username = "user5", password = "\$2a\$10\$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu")  // password: admin
        )
        userRepository.saveAll(users)

        // Генерация дел (20 записей)
        val cases = mutableListOf<Case>()
        repeat(20) { i ->
            val randomCourt = courts.random()
            cases.add(
                Case(
                    number = "А${40 + i}-${1000 + i}/2023",
                    status = CaseStatus.values().random(),
                    priority = CasePriority.values().random(),
                    facilityAddress = listOf(
                        "г. Москва, ул. Ленина, д. ${10 + i}",
                        "г. Санкт-Петербург, Невский пр-т, д. ${20 + i}",
                        "г. Новосибирск, ул. Советская, д. ${30 + i}",
                        "г. Екатеринбург, ул. Малышева, д. ${40 + i}",
                        "г. Казань, ул. Баумана, д. ${50 + i}"
                    ).random(),
                    court = if (Random.nextBoolean()) randomCourt else null,
                    judge = if (Random.nextBoolean()) judges.filter { it.court == randomCourt }.random() else null,
                    company = companies.random(),
                    region = regions.random(),
                    createdBy = users.random(),
                    createdAt = Instant.now().minusSeconds(Random.nextLong(86400 * 30)),
                    deadline = Instant.now().plusSeconds(Random.nextLong(86400 * 30)),
                    inspections = mutableListOf()
                )
            )
        }
        val savedCases = caseRepository.saveAll(cases)

        savedCases.forEach { case ->
            createInspection(case.id!!, case.facilityAddress)
        }
    }

    private fun createInspection(caseId: Long, address: String) {
        val inspection2 = Inspection(
            address = address,
            performedDate = Instant.now().minusSeconds(86400),
            case = caseRepository.findById(caseId).orElse(null),
        )
        inspectionRepository.save(inspection2)
    }
}