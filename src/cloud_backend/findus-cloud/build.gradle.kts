val ktorVersion: String by rootProject
val kotlinVersion: String by rootProject
val logbackVersion: String by rootProject

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.6.0"
    id("org.jetbrains.dokka") version "1.5.30"
}
version "1.0-SNAPSHOT"
application {
    mainClass.set("de.h_da.fbi.findus.ApplicationKt")
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation ("io.insert-koin:koin-ktor:3.1.4")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-serialization:1.6.7")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-client-gson:$ktorVersion")
    implementation("org.litote.kmongo:kmongo:4.3.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("org.jetbrains.dokka:kotlin-as-java-plugin:1.5.31")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.6")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    implementation("org.apache.commons:commons-csv:1.9.0")
    implementation ("org.mockito:mockito-core:2.27.0")
    implementation ("org.mockito:mockito-inline:2.27.0")
    implementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("com.github.papsign:Ktor-OpenAPI-Generator:-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.ktor:ktor-client-core:$ktorVersion")
    testImplementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation ("io.insert-koin:koin-test:3.1.4")
    testImplementation("org.mockito:mockito-inline:4.1.0")
}
