package com.prikshit.weatherdemo.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class,
    LocalPersistenceModule::class,
    UIModule::class,
    AppModule::class
])
interface WeatherAppComponent: AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): WeatherAppComponent
    }
    override fun inject(app: WeatherApplication)
}