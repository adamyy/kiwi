package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.di.features.AuthSubComponent
import adamyy.github.com.kiwi.di.features.TimelineSubComponent
import adamyy.github.com.kiwi.di.features.WelcomeSubComponent
import adamyy.github.com.kiwi.home.TimelineFragment
import adamyy.github.com.kiwi.login.AuthFragment
import adamyy.github.com.kiwi.login.WelcomeFragment
import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Binding to all the sub component builders
 */
@Module
abstract class BuildersModule {

    @Binds @IntoMap @FragmentKey(WelcomeFragment::class)
    abstract fun bindWelcomeFragmentInjectorFactory(builder: WelcomeSubComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds @IntoMap @FragmentKey(AuthFragment::class)
    abstract fun bindAuthFragmentInjectorFactory(builder: AuthSubComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds @IntoMap @FragmentKey(TimelineFragment::class)
    abstract fun bindTimelineFragmentInjectorFactory(builder: TimelineSubComponent.Builder): AndroidInjector.Factory<out Fragment>
}