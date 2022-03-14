import Deps.Koin.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.rickclephas.kmp.nativecoroutines")

}

// CocoaPods requires the podspec to have a version.
version = "1.0"

android {
    compileSdk = Versions.androidCompileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Workaround for https://youtrack.jetbrains.com/issue/KT-43944
android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}
dependencies {

}

kotlin {
    val sdkName: String? = System.getenv("SDK_NAME")

    android()
    jvm()

    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        sourceSets["commonMain"].dependencies {
            implementation(Deps.Kotlinx.coroutinesCore) {
                isForce = true
            }


            with(Deps.Ktor) {
                implementation(clientCore)
                implementation(clientJson)
                implementation(clientLogging)
                implementation(clientSerialization)
            }

            with(Deps.Kotlinx) {
                implementation(serializationCore)
                implementation(datetime)
            }

            with(Deps.Koin) {
                api(core)
                api(test)
            }

            with(Deps.Log) {
                api(kermit)
            }
        }
        sourceSets["commonTest"].dependencies {
        }

        sourceSets["androidMain"].dependencies {
            implementation(Deps.Ktor.clientAndroid)
        }
        sourceSets["androidTest"].dependencies {
            // having issue with following after update to Kotlin 1.5.21
            // need to investigate further
            //implementation(Deps.Test.kotlinTest)
            //implementation(Deps.Test.kotlinTestJUnit)
            implementation(Deps.Test.junit)
        }

        sourceSets["jvmMain"].dependencies {
            implementation(Deps.Ktor.clientJava)
            implementation(Deps.Log.slf4j)
        }

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

