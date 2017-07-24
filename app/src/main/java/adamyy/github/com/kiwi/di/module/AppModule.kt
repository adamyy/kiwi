package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.KiwiApplication
import adamyy.github.com.kiwi.data.source.preferences.BasePref
import adamyy.github.com.kiwi.di.features.AuthSubComponent
import adamyy.github.com.kiwi.di.features.TimelineSubComponent
import adamyy.github.com.kiwi.di.features.WelcomeSubComponent
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = arrayOf(
        WelcomeSubComponent::class,
        AuthSubComponent::class,
        TimelineSubComponent::class
))
class AppModule {

    @Provides @Singleton
    fun provideApplicationContext(application: KiwiApplication): Context {
        return application.applicationContext
    }

    @Provides @Singleton
    fun provideSharedPreferences(application: KiwiApplication): SharedPreferences {
        return application.getSharedPreferences(BasePref.NAME, Context.MODE_PRIVATE)
    }

    @Provides @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }
}