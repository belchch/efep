# Сборка (с использованием Gradle)
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /app
COPY .. .

# Убедитесь, что используете правильную версию Gradle (например, 8.5+)
RUN ./gradlew bootJar -x test --no-daemon

# Финальный образ (только с JAR)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Копируем собранный JAR
COPY --from=builder /app/build/libs/*.jar app.jar

# Порт Spring Boot (по умолчанию 8080)
EXPOSE 8080

# Запуск (можно добавить параметры JVM для Java 21)
ENTRYPOINT ["java", "-jar", "app.jar"]