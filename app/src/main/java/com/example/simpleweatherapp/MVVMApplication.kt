package com.example.simpleweatherapp

import android.app.Application
import com.example.simpleweatherapp.data.db.AppDatabase
import com.example.simpleweatherapp.data.network.MyApi
import com.example.simpleweatherapp.data.network.NetworkConnectionInterceptor
import com.example.simpleweatherapp.data.preferences.PreferenceProvider
import com.example.simpleweatherapp.data.repositories.WeatherRepository
import com.example.simpleweatherapp.viewmodel.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { WeatherRepository(instance(), instance(), instance()) }
        bind() from provider { WeatherViewModelFactory(instance()) }


    }

}