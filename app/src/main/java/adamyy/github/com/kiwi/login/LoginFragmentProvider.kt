package adamyy.github.com.kiwi.login

import adamyy.github.com.kiwi.login.auth.AuthFragment
import adamyy.github.com.kiwi.login.auth.AuthSubComponent
import adamyy.github.com.kiwi.login.welcome.WelcomeFragment
import adamyy.github.com.kiwi.login.welcome.WelcomeSubComponent
import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module
abstract class LoginFragmentProvider {

    @Binds @IntoMap @FragmentKey(AuthFragment::class)
    abstract fun provideAuthFragmentInjectorFactory(builder: AuthSubComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds @IntoMap @FragmentKey(WelcomeFragment::class)
    abstract fun provideWelcomeFragmentInjectorFactory(builder: WelcomeSubComponent.Builder): AndroidInjector.Factory<out Fragment>

}