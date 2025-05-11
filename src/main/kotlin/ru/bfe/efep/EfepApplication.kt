package ru.bfe.efep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EfepApplication

fun main(args: Array<String>) {
    runApplication<EfepApplication>(*args)
}
