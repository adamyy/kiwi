package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.KiwiApplication
import adamyy.github.com.kiwi.di.component.AuthSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = arrayOf(AuthSubComponent::class))
class AppModule(private val app: KiwiApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext() = app

}