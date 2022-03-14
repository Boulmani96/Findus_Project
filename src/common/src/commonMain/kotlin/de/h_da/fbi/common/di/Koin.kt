package de.h_da.fbi.common.di

import co.touchlab.kermit.CommonLogger
import co.touchlab.kermit.Kermit
import de.h_da.fbi.common.remote.FindusApi
import de.h_da.fbi.common.repository.FindusRepository
import de.h_da.fbi.common.repository.FindusRepositoryInterface

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

const val DEFAULT_URL = "http://10.0.2.2:8082/api"

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

fun commonModule(enableNetworkLogs: Boolean, baseUrl: String = DEFAULT_URL) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs = enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single<FindusRepositoryInterface> { FindusRepository() }

    single { FindusApi(client = get(), baseUrl) }
    single { Kermit(logger = get()) }
    single<co.touchlab.kermit.Logger> { CommonLogger() }

}

fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}
