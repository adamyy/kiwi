package adamyy.github.com.kiwi.login

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(
        LoginModule::class,
        LoginFragmentProvider::class
))
interface LoginSubComponent: AndroidInjector<LoginActivity> {

    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<LoginActivity>()

}