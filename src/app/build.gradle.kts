import org.jetbrains.kotlin.gradle.plugin.statistics.ReportStatisticsToElasticSearch.url

plugins {
    id("com.android.application")
    kotlin("android")
    id("com.github.ben-manes.versions")
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
}

android {
    compileSdk = Versions.androidCompileSdk

    defaultConfig {
        applicationId = "de.h_da.fbi.findus"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation("androidx.datastore:datastore-core:1.0.0-alpha08")
    with(Deps.Android) {
        implementation(material)
    }

    with(Deps.AndroidX) {
        implementation(lifecycleRuntimeKtx)
        implementation(lifecycleViewmodelKtx)
        implementation(activityCompose)
    }

    implementation("androidx.glance:glance-appwidget:1.0.0-SNAPSHOT")
    //Charts
    implementation ("com.github.tehras:charts:master-SNAPSHOT")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.navigation:navigation-compose:2.4.0-beta02")
    implementation("io.ktor:ktor-server-core:1.6.5")
    implementation("io.ktor:ktor-server-netty:1.6.5")
    testImplementation("io.ktor:ktor-server-tests:1.6.5")
    implementation("io.ktor:ktor-serialization:1.6.5")
    implementation("io.ktor:ktor-client-gson:1.6.5")


    with(Deps.Compose) {
        implementation(ui)
        implementation(uiGraphics)
        implementation(foundationLayout)
        implementation(material)
        implementation(navigation)
        implementation(coilCompose)
        implementation(accompanistNavigationAnimation)
        implementation(uiTooling)
    }



    with(Deps.Koin) {
        implementation(core)
        implementation(android)
        implementation(compose)
        testImplementation(test)
        testImplementation(testJUnit4)
    }

    with(Deps.Test) {
        testImplementation(junit)
        androidTestImplementation(androidXTestJUnit)
        testImplementation(testCore)
        testImplementation(robolectric)
        testImplementation(mockito)

        // Compose testing dependencies
        androidTestImplementation(composeUiTest)
        androidTestImplementation(composeUiTestJUnit)
        debugImplementation(composeUiTestManifest)
    }
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.compose.ui:ui:1.0.5")
    implementation("androidx.compose.material:material:1.0.5")
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.4.0")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.5")

    debugImplementation("androidx.compose.ui:ui-test-manifest:1.0.5")
    testImplementation("com.google.truth:truth:1.1")


    // KMongo
    implementation("org.litote.kmongo:kmongo:4.3.0")
    implementation("org.litote.kmongo:kmongo-async:4.3.0")
    implementation("org.litote.kmongo:kmongo-coroutine:4.3.0")
    implementation("org.litote.kmongo:kmongo-reactor:4.3.0")
    implementation("org.litote.kmongo:kmongo-rxjava2:4.3.0")
    testImplementation("junit:junit:4.13.2")

    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")

    //Ktor
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("io.ktor:ktor-client-okhttp:1.5.4")
    implementation("io.ktor:ktor-client-json:1.5.4")
    implementation("io.ktor:ktor-client-serialization-jvm:1.5.4")
    implementation("io.ktor:ktor-client-logging-jvm:1.5.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")

    implementation("io.ktor:ktor-client-core:1.5.4")
    implementation("io.ktor:ktor-client-cio:1.5.4")

    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-core:3.1.4")
    // Testing
    testImplementation("io.insert-koin:koin-test:3.1.4")

    implementation(project(":common"))

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.22.0-rc")

    implementation("com.google.accompanist:accompanist-pager:0.15.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.15.0")

    //Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.21.1-beta")

    //Gifs
    implementation ("com.airbnb.android:lottie-compose:4.2.2")

}

repositories {
    mavenCentral()
}
