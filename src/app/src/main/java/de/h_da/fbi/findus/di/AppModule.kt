package de.h_da.fbi.findus.di

import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import de.h_da.fbi.findus.ui.FindusViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { FindusViewModel() }
    single<Logger> { LogcatLogger() }
}
