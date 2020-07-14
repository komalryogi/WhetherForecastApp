package com.almissbah.weather.di.component.module

import android.app.Application
import com.almissbah.weather.utils.location.LocationLiveData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UtilsModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideLocationLiveData(
        app: Application
    ): LocationLiveData {
        return LocationLiveData(app.baseContext)
    }


}