package adamyy.github.com.kiwi.home

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(
        HomeModule::class,
        HomeFragmentProvider::class
))
interface HomeSubComponent : AndroidInjector<HomeActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<HomeActivity>()

}