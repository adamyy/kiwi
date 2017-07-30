package adamyy.github.com.kiwi.di

import adamyy.github.com.kiwi.KiwiApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        AuthModule::class,
        BuildersModule::class,
        NetworkModule::class,
        PrefsModule::class,
        RepositoryModule::class
))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: KiwiApplication): Builder
        fun build(): AppComponent
    }

    fun inject(app: KiwiApplication)
}