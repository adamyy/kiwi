package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.di.component.AuthSubComponent
import adamyy.github.com.kiwi.ui.features.login.AuthFragment
import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module
abstract class BuildersModule {

    @Binds
    @IntoMap
    @FragmentKey(AuthFragment::class)
    abstract fun bindAuthFragmentInjectorFactory(builder: AuthSubComponent.Builder): AndroidInjector.Factory<out Fragment>

    // add bindings for other sub components

}