package adamyy.github.com.kiwi.di

import adamyy.github.com.kiwi.BuildConfig
import adamyy.github.com.kiwi.KiwiApplication
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides @Singleton
    fun provideSessionManager(authPref: AuthPref, application: KiwiApplication): SessionManager {
        return SessionManager(authPref, application, BuildConfig.API_KEY, BuildConfig.API_SECRET, BuildConfig.API_AUTH_URL)
    }
}