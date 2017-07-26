package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.data.repository.StatusRepository
import adamyy.github.com.kiwi.data.repository.StatusRepositoryImpl
import adamyy.github.com.kiwi.data.repository.UserRepository
import adamyy.github.com.kiwi.data.repository.UserRepositoryImpl
import adamyy.github.com.kiwi.data.source.network.StatusApi
import adamyy.github.com.kiwi.data.source.network.UserApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }

    @Provides @Singleton
    fun provideStatusRepository(statusApi: StatusApi): StatusRepository {
        return StatusRepositoryImpl(statusApi)
    }
}