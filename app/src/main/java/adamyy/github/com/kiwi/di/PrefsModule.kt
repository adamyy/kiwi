package adamyy.github.com.kiwi.di

import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import adamyy.github.com.kiwi.data.source.preferences.KiwiPreferences
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides @Singleton
    fun provideAuthPrefs(pref: SharedPreferences, gson: Gson): AuthPref {
        return KiwiPreferences(pref, gson)
    }
}