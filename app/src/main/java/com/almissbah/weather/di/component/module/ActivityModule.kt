package com.almissbah.weather.di.component.module

import com.almissbah.weather.ui.MainActivity
import com.almugdad.takhlesy.di.module.FragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

}