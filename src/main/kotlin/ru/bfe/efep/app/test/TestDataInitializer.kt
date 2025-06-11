package ru.bfe.efep.app.test

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ru.bfe.efep.app.inspection.Inspection
import ru.bfe.efep.app.inspection.InspectionRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDoc
import ru.bfe.efep.app.inspection.photodoc.PhotoDocRepository
import ru.bfe.efep.app.inspection.photodoc.PhotoDocType
import ru.bfe.efep.app.material.Material
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.spot.Spot
import ru.bfe.efep.app.spot.SpotRepository
import ru.bfe.efep.app.spot.room.Room
import ru.bfe.efep.app.spot.room.RoomRepository
import ru.bfe.efep.app.structelem.StructElem
import ru.bfe.efep.app.structelem.StructElemRepository
import java.time.Instant

class TestDataInitializer(
    private val spotRepository: SpotRepository,
    private val materialRepository: MaterialRepository,
    private val structElemRepository: StructElemRepository,
    private val inspectionRepository: InspectionRepository,
    private val photoDocRepository: PhotoDocRepository,
    private val roomRepository: RoomRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {

        spotRepository.saveAll(
            listOf(
                Spot(name = "Адресная табличка"),
                Spot(name = "Подъезд"),
                Spot(name = "Нумирация квартир"),
                Spot(name = "Дверь в квартиру"),
            )
        )

        roomRepository.saveAll(
            listOf(
                Room(name = "Холл"),
                Room(name = "Санузел"),
                Room(name = "Жилая комната"),
                Room(name = "Кухня"),
                Room(name = "Кухня-гостиная"),
            )
        )

        // Создание инспекций
        val inspections = (1..2).map { i ->
            Inspection(
                address = "Address $i",
                performedDate = Instant.now()
            )
        }.let { inspectionRepository.saveAll(it) }

        // Добавление фото к каждой инспекции
        /*inspections.forEachIndexed { index, inspection ->
            // Создаем дефектные фото (DEFECT)
            val defectPhotos = (1..3).map { i ->
                PhotoDoc(
                    source = "defect_${index + 1}_$i.jpg",
                    inspection = inspection,
                    spot = spots.random(),
                    material = materials.random(),
                    structElem = structElems.random(),
                    type = PhotoDocType.DEFECT
                )
            }

            // Создаем общие фото (GENERAL_VIEW)
            val generalPhotos = (1..2).map { i ->
                PhotoDoc(
                    source = "general_${index + 1}_$i.jpg",
                    inspection = inspection,
                    spot = spots.random(),  // Для GENERAL_VIEW spot может быть null
                    material = null,  // Обязательно null для GENERAL_VIEW
                    structElem = null, // Обязательно null для GENERAL_VIEW
                    type = PhotoDocType.GENERAL_VIEW
                )
            }

            // Создаем фото без типа (для тестирования валидации)
            val untaggedPhoto = PhotoDoc(
                source = "untagged_${index + 1}.jpg",
                inspection = inspection,
                spot = null,
                material = null,
                structElem = null,
                type = null
            )

            photoDocRepository.saveAll(defectPhotos + generalPhotos + listOf(untaggedPhoto))
        }*/

        println("Test data successfully initialized.")
    }
}
