package ru.bfe.efep.app.test

import jakarta.transaction.Transactional
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.bfe.efep.app.defect.Defect
import ru.bfe.efep.app.defect.DefectRepository
import ru.bfe.efep.app.defect.flaw.Flaw
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.material.Material
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.structelem.StructElem
import ru.bfe.efep.app.structelem.StructElemRepository

@Component
class DefectDataInitializer(
    private val materialRepository: MaterialRepository,
    private val structElemRepository: StructElemRepository,
    private val flawRepository: FlawRepository,
    private val defectRepository: DefectRepository
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        // Список случайных ГОСТов для использования в стандартах
        val gostStandards = listOf(
            "ГОСТ 31173-2016 п. 5.3.11",
            "ГОСТ 30494-2011 п. 4.12",
            "ГОСТ 30971-2012 п. 6.5.3",
            "ГОСТ 52748-2007 п. 3.2.4",
            "ГОСТ 32567-2013 п. 7.1.9",
            "ГОСТ 23166-99 п. 5.4.2",
            "ГОСТ 30673-99 п. 8.3.5",
            "ГОСТ 24866-2014 п. 4.7.1",
            "ГОСТ 30777-2001 п. 6.2.3",
            "ГОСТ 30970-2002 п. 5.1.8"
        )

        // Функция для получения случайного ГОСТа
        fun randomGost() = gostStandards.random()

        // Создаем материалы
        val materials = listOf(
            Material(name = "Дерево"),
            Material(name = "ПВХ"),
            Material(name = "Плитка"),
            Material(name = "Ламинат"),
            Material(name = "Обои")
        ).also { materialRepository.saveAll(it) }

        // Создаем структурные элементы
        val structElems = listOf(
            StructElem(name = "Окно", materials = listOf(materials[0], materials[1])),
            StructElem(name = "Пол", materials = listOf(materials[0], materials[2], materials[3])),
            StructElem(name = "Стена", materials = listOf(materials[0], materials[4]))
        ).also { structElemRepository.saveAll(it) }

        // Создаем дефекты (flaws)
        val flaws = listOf(
            Flaw(name = "Царапина"),
            Flaw(name = "Скол"),
            Flaw(name = "Зазор"),
            Flaw(name = "Трещина"),
            Flaw(name = "Отслоение"),
            Flaw(name = "Деформация"),
            Flaw(name = "Пятно")
        ).also { flawRepository.saveAll(it) }

        // Создаем дефекты (defects) с логичными связями
        val defects = listOf(
            Defect(
                template = "Царапины на поверхности окна",
                standard = "${randomGost()} - Не должно быть царапин длиной более 5 мм",
                structElem = structElems[0],
                material = materials[1],
                flaw = flaws[0]
            ),
            Defect(
                template = "Сколы на деревянном окне",
                standard = "${randomGost()} - Не должно быть сколов глубиной более 2 мм",
                structElem = structElems[0],
                material = materials[0],
                flaw = flaws[1]
            ),
            Defect(
                template = "Зазор между стеной и полом",
                standard = "${randomGost()} - Зазор не должен превышать 3 мм",
                structElem = structElems[1],
                material = null,
                flaw = flaws[2]
            ),
            Defect(
                template = "Трещины в плитке на полу",
                standard = "${randomGost()} - Не должно быть трещин любого размера",
                structElem = structElems[1],
                material = materials[2],
                flaw = flaws[3]
            ),
            Defect(
                template = "Отслоение обоев на стене",
                standard = "${randomGost()} - Не должно быть участков отслоения более 10 см²",
                structElem = structElems[2],
                material = materials[4],
                flaw = flaws[4]
            ),
            Defect(
                template = "Деформация деревянного пола",
                standard = "${randomGost()} - Прогиб не должен превышать 2 мм на 1 м",
                structElem = structElems[1],
                material = materials[0],
                flaw = flaws[5]
            ),
            Defect(
                template = "Пятна на ламинате",
                standard = "${randomGost()} - Не должно быть пятен площадью более 5 см²",
                structElem = structElems[1],
                material = materials[3],
                flaw = flaws[6]
            )
        ).also { defectRepository.saveAll(it) }
    }
}