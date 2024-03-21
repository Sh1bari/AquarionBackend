FROM maven:3.8.4-openjdk-17 AS build

ARG PORT
ARG DATABASE_NAME
ARG DATABASE_USER
ARG DATABASE_PASSWORD
ARG MINIO_ENDPOINT
ARG MINIO_ACCESS_KEY
ARG MINIO_SECRET_KEY
ARG MINIO_BUCKET
ARG INIT
ARG SERVER_NAME
ARG SECRET
ARG SUPPORT_URL
ARG KAFKA_SEND_TO_TOPIC
ARG KAFKA_READ_FROM_TOPIC
ENV PORT=${PORT}
ENV DATABASE_NAME=${DATABASE_NAME}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV MINIO_ENDPOINT=${MINIO_ENDPOINT}
ENV MINIO_ACCESS_KEY=${MINIO_ACCESS_KEY}
ENV MINIO_SECRET_KEY=${MINIO_SECRET_KEY}
ENV MINIO_BUCKET=${MINIO_BUCKET}
ENV INIT=${INIT}
ENV SERVER_NAME=${SERVER_NAME}
ENV SECRET=${SECRET}
ENV SUPPORT_URL=${SUPPORT_URL}
ENV KAFKA_SEND_TO_TOPIC=${KAFKA_SEND_TO_TOPIC}
ENV KAFKA_READ_FROM_TOPIC=${KAFKA_READ_FROM_TOPIC}
ARG JAR_FILE=*.jar
WORKDIR /build
COPY src src
COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package && \
    java -Djarmode=layertools \
    -jar target/${JAR_FILE} \
    extract --destination target/extracted

FROM bellsoft/liberica-openjdk-debian:17
WORKDIR /application
COPY --from=build /build/target/extracted/application .
COPY --from=build /build/target/extracted/dependencies .
COPY --from=build /build/target/extracted/snapshot-dependencies .
COPY --from=build /build/target/extracted/spring-boot-loader .
ENTRYPOINT exec java org.springframework.boot.loader.JarLauncher ${0} ${@}