package adamyy.github.com.kiwi.di.component

import adamyy.github.com.kiwi.di.module.NetworkModule
import adamyy.github.com.kiwi.ui.features.login.AuthFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(NetworkModule::class))
interface AuthSubComponent : AndroidInjector<AuthFragment> {

    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<AuthFragment>()

}