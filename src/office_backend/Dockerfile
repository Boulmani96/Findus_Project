FROM gradle:7.0.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
COPY . . /home/gradle/src/
RUN gradle ktor-server:clean ktor-server:installDist --no-daemon
EXPOSE 8082
WORKDIR  /home/gradle/src/ktor-server/build/install/ktor-server/bin
CMD ["./ktor-server"]
