package ru.bfe.efep.app.test

import jakarta.transaction.Transactional
import org.postgresql.core.JavaVersion
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.bfe.efep.app.defect.Defect
import ru.bfe.efep.app.defect.DefectRepository
import ru.bfe.efep.app.defect.flaw.Flaw
import ru.bfe.efep.app.defect.flaw.FlawRepository
import ru.bfe.efep.app.material.Material
import ru.bfe.efep.app.material.MaterialRepository
import ru.bfe.efep.app.standard.Standard
import ru.bfe.efep.app.standard.StandardRepository
import ru.bfe.efep.app.structelem.StructElem
import ru.bfe.efep.app.structelem.StructElemRepository

class DefectDataInitializer2(
    private val materialRepository: MaterialRepository,
    private val structElemRepository: StructElemRepository,
    private val flawRepository: FlawRepository,
    private val defectRepository: DefectRepository,
    private val standardRepository: StandardRepository
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        val standard_31171 = standardRepository.save(
            Standard(name = "ГОСТ 31173-2016 г. 6", description = "yе должно превышать 1,5 мм на 1 м длины, но не более 3 мм на высоту изделия")
        )
        val standard_31173_51311 = standardRepository.save(
            Standard(name = "ГОСТ 31173-2016 п. 5.3.11 ")
        )
        val standard_475_537 = standardRepository.save(
            Standard(name="ГОСТ 475-2016 п. 5.3.7 ", description = "не более 0,3 мм")
        )
        val standard_7113330_7615 = standardRepository.save(
            Standard(name="СП 71.13330.2017 п. 7.6.15", description = "наличие дефекта не допускается")
        )
        val standard_7113330_t74 = standardRepository.save(
            Standard(name="СП 71.13330.2017 Табл. 7.4", description = "не более 2 мм")
        )
        val standard_7113330_t815 = standardRepository.save(
            Standard(name="СП 71.13330.2017 Табл. 8.15 ", description = "наличие дефекта не допускается")
        )
        val standard_7113330_t815_114 = standardRepository.save(
            Standard(name="СП 71.13330.2017 Табл. 8.15; ТР 114–01 п. 7.4", description = "наличие дефекта не допускается")
        )
        val standard_7113330_t815_2 = standardRepository.save(
            Standard(name="СП 71.13330.2017 Табл. 8.15", description = "не более 0,2% соответствующего размера помещения, но не более 10 мм")
        )
        val standard_475_564_567 = standardRepository.save(
            Standard(name="ГОСТ 475-2016 п. 5.6.4, п. 5.6.7")
        )
        val standard_475_5484_567 = standardRepository.save(
            Standard(name="ГОСТ 475-2016 п. 5.4.8, п. 5.6.7")
        )
        val standard_475_5484 = standardRepository.save(
            Standard(name="ГОСТ 475–2016 п.5.4.8", description = "наличие дефекта не допускается")
        )
        val standard_475_534 = standardRepository.save(
            Standard(name="ГОСТ 475-2016 п.5.3.4", description = "св. 1600 до 2500 мм не более 2мм")
        )
        val standard_13330_7417 = standardRepository.save(
            Standard(name="СП 71.13330.2017 п. 7.4.17", description = "не более 1,5 мм ")
        )
        val standard_13330_7413 = standardRepository.save(
            Standard(name="СП 71.13330.2017 п.7.4.13", description = "наличие дефекта не допускается")
        )
        val standard_34378_pl = standardRepository.save(
            Standard(name="ГОСТ 34378-2018 прил. Л", description = "не должно превышать 1,5 мм на 1 пог.м, но не более 3,0 мм на всю длину изделия")
        )
        val standard_7113330_755_t77 = standardRepository.save(
            Standard(name="СП 71.13330.2017 п. 7.5.5 Таблица 7.7", description = "наличие дефекта не допускается")
        )
        val standard_7113330_782 = standardRepository.save(
            Standard(name="СП 71.13330.2017 п. 7.8.2", description = "наличие дефекта не допускается")
        )
        val standard_730674_547 = standardRepository.save(
            Standard(name="ГОСТ 30674-23 п.5.4.7 ")
        )


        val emu = materialRepository.save(
            Material(name = "Эмульсинная окраска")
        )

        val wallpaper = materialRepository.save(
            Material(name = "Обои")
        )

        val laminate = materialRepository.save(
            Material(name = "Ламинат")
        )

        val tile = materialRepository.save(
            Material(name = "Плитка")
        )

        val alum = materialRepository.save(
            Material(name = "Профиль алюминиевый")
        )

        val nataj = materialRepository.save(
            Material(name = "Натяжной")
        )

        val pvh = materialRepository.save(
            Material(name = "Профиль ПВХ")
        )

        val metal = materialRepository.save(
            Material(name = "Металл")
        )

        val wood = materialRepository.save(
            Material(name = "Дерево")
        )

        val mainDoor = structElemRepository.save(
            StructElem(name = "Дверь входная", materials = listOf(alum, pvh, metal, wood)),
        )

        val door = structElemRepository.save(
            StructElem(name = "Дверь межкомнатная", materials = listOf(alum, pvh, metal, wood)),
        )

        val window = structElemRepository.save(
            StructElem(name = "Оконный блок", materials = listOf(alum, pvh, wood)),
        )

        val wall = structElemRepository.save(
            StructElem(name = "Стена", materials = listOf(wallpaper, metal, wood, tile, emu)),
        )

        val floor = structElemRepository.save(
            StructElem(name = "Пол", materials = listOf(laminate, wood, tile)),
        )

        val warming = structElemRepository.save(
            StructElem(name = "Система отопления", materials = listOf()),
        )

        val ceiling = structElemRepository.save(
            StructElem(name = "Потолок", materials = listOf(emu, nataj)),
        )

        val otk = structElemRepository.save(
            StructElem(name = "Откос", materials = listOf(pvh, wood, emu, metal, )),
        )

        val otklan = flawRepository.save(Flaw(name = "Отклонение"))
        val deform = flawRepository.save(Flaw(name = "Деформация"))
        val nerov = flawRepository.save(Flaw(name = "Неровность"))
        val gryaz = flawRepository.save(Flaw(name = "Загрязнение"))
        val zazor = flawRepository.save(Flaw(name = "Зазор"))
        val zar = flawRepository.save(Flaw(name = "Царапина"))
        val def  = flawRepository.save(Flaw(name = "Повреждение"))


        //{{ПРИЧИНА}} {{ЗНАЧЕНИЕ}}
        val defects = listOf(
            Defect(
                template = "Отклонение дверного блока от вертикали и горизонтали {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство) ",
                standard = standard_31171,
                structElem = mainDoor,
                flaw = otklan,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Деформация полотна входной двери (причина возникновения: производство)",
                standard = standard_31173_51311,
                structElem = mainDoor,
                flaw = deform,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Следы краски, герметика, клея (причина возникновения: производство) ",
                standard = standard_31173_51311,
                structElem = mainDoor,
                flaw = gryaz,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Зазоры в местах неподвижных соединений элементов дверных блоков более {{ЗНАЧЕНИЕ}} мм (причина возникновения: производство) ",
                standard = standard_475_537,
                structElem = mainDoor,
                flaw = zazor,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Наличие воздушных пузырей, замятин, доклеек и отслоения (причина возникновения: производство)",
                standard = standard_7113330_7615,
                structElem = wall,
                material = wallpaper,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Отклонение от вертикали {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство ",
                standard = standard_7113330_t74,
                structElem = wall,
                material = wallpaper,
                flaw = otklan,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Механические повреждения (причина возникновения: эксплуатация)",
                standard = standard_7113330_t815,
                structElem = floor,
                material = laminate,
                flaw = def,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Приподнятые кромки покрытия (причина возникновения: производство)",
                standard = standard_7113330_t815_114,
                structElem = floor,
                material = laminate,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Отклонения от заданного уклона покрытий при измерении 2-метровой рейкой {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство)",
                standard = standard_7113330_t815_114,
                structElem = floor,
                material = laminate,
                flaw = otklan,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Зазоры в местах неподвижных соединений элементов дверных блоков более 1 мм (причина возникновения: производство) ",
                standard = standard_475_537,
                structElem = door,
                flaw = zazor,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Царапины, сколы (механические повреждения) (причина возникновения: эксплуатация)",
                standard = standard_475_564_567,
                structElem = door,
                flaw = zar,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Следы краски, герметика, клея (причина возникновения: производство) ",
                standard = standard_475_5484_567,
                structElem = door,
                flaw = gryaz,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Ненадлежащая установка наличника дверного блока  (причина возникновения: производство)",
                standard = standard_475_5484,
                structElem = door,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Отклонение дверного полотна/коробки от плоскостности/прямолинейности {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство)",
                standard = standard_475_534,
                structElem = door,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Отклонение от вертикали {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство)  ",
                standard = standard_13330_7417,
                structElem = wall,
                material = tile,
                flaw = otklan,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Неравномерные швы между плитками (причина возникновения: производство)",
                standard = standard_13330_7413,
                structElem = floor,
                material = tile,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Изменение характера звучания плитки при простукивании (причина возникновения: производство)",
                standard = standard_7113330_t815,
                structElem = floor,
                material = tile,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Отклонение от вертикали профиля оконного блока {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство)",
                standard = standard_34378_pl,
                structElem = window,
                material = alum,
                flaw = otklan,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Неравномерное окрашивание труб, с видимыми участками непрокраса, брызгами, потеками, полосами, следами от кисти  (причина возникновения: производство)",
                standard = standard_7113330_755_t77,
                structElem = warming,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Неровности поверхности натяжного полотна, складки  (причина возникновения: производство) ",
                standard = standard_7113330_782,
                structElem = ceiling,
                material = nataj,
                flaw = nerov,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Приподнятые кромки покрытия (причина возникновения: производство)",
                standard = standard_7113330_t815_114,
                structElem = floor,
                material = laminate,
                hasValue = false,
                hasCause = false
            ),

            Defect(
                template = "Механические повреждения ПВХ профиля  (причина возникновения: производство)",
                standard = standard_730674_547,
                structElem = otk,
                material = pvh,
                flaw = def,
                hasValue = false,
                hasCause = false
            ),
            Defect(
                template = "Отклонение от вертикали профиля оконного блока {{ЗНАЧЕНИЕ}} мм на 1м (причина возникновения: производство)",
                standard = standard_34378_pl,
                structElem = otk,
                material = pvh,
                flaw = otklan,
                hasValue = true,
                hasCause = false
            ),
            Defect(
                template = "Следы краски  (причина возникновения: производство)",
                standard = standard_730674_547,
                structElem = otk,
                material = pvh,
                flaw = gryaz,
                hasValue = false,
                hasCause = false
            )

        ).also { defectRepository.saveAll(it) }
    }
}