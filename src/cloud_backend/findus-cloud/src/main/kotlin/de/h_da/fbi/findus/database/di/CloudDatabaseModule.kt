package de.h_da.fbi.findus.database.di

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ReadPreference
import io.github.cdimascio.dotenv.dotenv
import org.koin.dsl.module
import org.litote.kmongo.KMongo
import java.util.concurrent.TimeUnit

const val mongo_URI = "MONGO_URI"
const val socketTimeOut = 3
const val clusterTimeOut: Long = 3

/**
 * this module contains the required logic to manage the communication between the cloud API and the
 * cloud database
 */
val cloudDatabaseModule = module(createdAtStart = true) {
    factory {
        val settings = MongoClientSettings.builder()
        settings.retryWrites(false)
        settings.applyToSocketSettings { builder ->
            run {
                builder.connectTimeout(socketTimeOut, TimeUnit.SECONDS)
                builder.readTimeout(socketTimeOut, TimeUnit.SECONDS)
            }
        }
        settings.applyToClusterSettings { builder ->
            builder.serverSelectionTimeout(
                clusterTimeOut,
                TimeUnit.SECONDS
            )
        }
        val connectionString = dotenv().get(mongo_URI)
        val uri = ConnectionString(connectionString)
        settings.applyConnectionString(uri)
        settings.readPreference(ReadPreference.primaryPreferred())
        settings.retryReads(false)
        KMongo.createClient(settings.build())
    }
}
