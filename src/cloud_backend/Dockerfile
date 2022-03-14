FROM gradle:7.0.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
COPY . . /home/gradle/src/
RUN gradle findus-cloud:clean findus-cloud:installDist --no-daemon
EXPOSE 8084
WORKDIR  /home/gradle/src/findus-cloud/build/install/findus-cloud/bin
CMD ["./findus-cloud"]
