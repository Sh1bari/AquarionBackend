FROM maven:3.8.4-openjdk-17 AS build

ARG PORT
ARG HOST
ENV PORT=${PORT}
ENV HOST=HOST

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