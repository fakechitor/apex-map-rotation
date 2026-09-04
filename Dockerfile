# Этап 1: Сборка приложения (Builder)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Копируем конфигурационные файлы Gradle для кэширования зависимостей
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts* build.gradle* settings.gradle.kts* settings.gradle* ./

# Делаем gradlew исполняемым и предзагружаем зависимости
RUN chmod +x ./gradlew && ./gradlew dependencies --no-daemon || true

# Копируем исходный код и собираем JAR без выполнения тестов
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# Этап 2: Запуск в легковесном контейнере
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Создаем непривилегированного пользователя для безопасности
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Копируем скомпилированный jar из builder-образа
COPY --from=builder /app/build/libs/*.jar app.jar

# Оптимальные флаги памяти и часового пояса для JVM
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Duser.timezone=Europe/Moscow"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
