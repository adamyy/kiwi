package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.data.repository.AuthRepository
import adamyy.github.com.kiwi.data.repository.AuthRepositoryImpl
import adamyy.github.com.kiwi.data.repository.UserRepository
import adamyy.github.com.kiwi.data.repository.UserRepositoryImpl
import adamyy.github.com.kiwi.data.source.network.AuthApi
import adamyy.github.com.kiwi.data.source.network.UserApi
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides @Singleton
    fun provideAuthRepository(authPref: AuthPref, authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authPref, authApi)
    }

    @Provides @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }
}