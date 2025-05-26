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
            StructElem(name = "Окно", materials = listOf(materials[0], materials[1])), // Дерево, ПВХ
            StructElem(name = "Пол", materials = listOf(materials[0], materials[2], materials[3])), // Дерево, Плитка, Ламинат
            StructElem(name = "Стена", materials = listOf(materials[0], materials[4])) // Дерево, Обои
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
                standard = "Не должно быть царапин длиной более 5 мм",
                structElem = structElems[0], // Окно
                material = materials[1], // ПВХ
                flaw = flaws[0] // Царапина
            ),
            Defect(
                template = "Сколы на деревянном окне",
                standard = "Не должно быть сколов глубиной более 2 мм",
                structElem = structElems[0], // Окно
                material = materials[0], // Дерево
                flaw = flaws[1] // Скол
            ),
            Defect(
                template = "Зазор между стеной и полом",
                standard = "Зазор не должен превышать 3 мм",
                structElem = structElems[1], // Пол
                material = null, // Может быть для любого материала
                flaw = flaws[2] // Зазор
            ),
            Defect(
                template = "Трещины в плитке на полу",
                standard = "Не должно быть трещин любого размера",
                structElem = structElems[1], // Пол
                material = materials[2], // Плитка
                flaw = flaws[3] // Трещина
            ),
            Defect(
                template = "Отслоение обоев на стене",
                standard = "Не должно быть участков отслоения более 10 см²",
                structElem = structElems[2], // Стена
                material = materials[4], // Обои
                flaw = flaws[4] // Отслоение
            ),
            Defect(
                template = "Деформация деревянного пола",
                standard = "Прогиб не должен превышать 2 мм на 1 м",
                structElem = structElems[1], // Пол
                material = materials[0], // Дерево
                flaw = flaws[5] // Деформация
            ),
            Defect(
                template = "Пятна на ламинате",
                standard = "Не должно быть пятен площадью более 5 см²",
                structElem = structElems[1], // Пол
                material = materials[3], // Ламинат
                flaw = flaws[6] // Пятно
            )
        ).also { defectRepository.saveAll(it) }
    }
}