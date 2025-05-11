package ru.bfe.efep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class EfepApplication

fun main(args: Array<String>) {
    runApplication<EfepApplication>(*args)
}
