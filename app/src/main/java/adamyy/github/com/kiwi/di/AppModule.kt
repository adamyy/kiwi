package adamyy.github.com.kiwi.di

import adamyy.github.com.kiwi.KiwiApplication
import adamyy.github.com.kiwi.data.source.preferences.BasePref
import adamyy.github.com.kiwi.home.HomeSubComponent
import adamyy.github.com.kiwi.login.LoginSubComponent
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = arrayOf(
        LoginSubComponent::class,
        HomeSubComponent::class
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
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.create()
    }
}