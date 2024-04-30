# syntax=docker/dockerfile:1.4

FROM --platform=$BUILDPLATFORM maven:3.8.5-eclipse-temurin-17 AS builder
WORKDIR /workdir/server
COPY pom.xml /workdir/server/pom.xml
RUN mvn dependency:go-offline && \
    rm -rf /root/.m2/repository

COPY src /workdir/server/src
RUN mvn install && \
    rm -rf /root/.m2/repository

FROM builder as dev-envs
# install Docker tools (cli, buildx, compose)
COPY --from=gloursdocker/docker / /
CMD ["mvn", "spring-boot:run"]

FROM builder as prepare-production
WORKDIR /workdir/server/target/dependency
RUN mkdir -p target/dependency && \
    jar -xf ../*.jar && \
    rm ../*.jar

FROM eclipse-temurin:17-jre-focal
EXPOSE 8080
VOLUME /tmp
ARG DEPENDENCY=/workdir/server/target/dependency
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.company.project.Application"]
