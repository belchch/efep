package ru.bfe.efep.app.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Configuration

@Configuration
class EnvConfig {
    init {
        val dotenv: Dotenv = Dotenv.configure().load()
        dotenv.entries().forEach({ entry -> System.setProperty(entry.getKey(), entry.getValue()) }
        )
    }
}