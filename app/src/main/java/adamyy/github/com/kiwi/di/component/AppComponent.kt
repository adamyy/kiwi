package adamyy.github.com.kiwi.di.component

import adamyy.github.com.kiwi.KiwiApplication
import adamyy.github.com.kiwi.di.module.AppModule
import adamyy.github.com.kiwi.di.module.BuildersModule
import adamyy.github.com.kiwi.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        NetworkModule::class,
        AppModule::class,
        AndroidSupportInjectionModule::class,
        BuildersModule::class
))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: KiwiApplication): Builder
        fun build(): AppComponent
    }

    fun inject(app: KiwiApplication)
}