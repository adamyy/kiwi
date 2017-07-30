package adamyy.github.com.kiwi.di

import adamyy.github.com.kiwi.home.HomeActivity
import adamyy.github.com.kiwi.home.HomeSubComponent
import adamyy.github.com.kiwi.login.LoginActivity
import adamyy.github.com.kiwi.login.LoginSubComponent
import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
 * Binding to all the sub component builders
 */
@Module
abstract class BuildersModule {

    @Binds @IntoMap @ActivityKey(LoginActivity::class)
    abstract fun bindLoginActivityInjectorFactory(builder: LoginSubComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds @IntoMap @ActivityKey(HomeActivity::class)
    abstract fun bindHomeActivityInjectorFactory(builder: HomeSubComponent.Builder): AndroidInjector.Factory<out Activity>
}