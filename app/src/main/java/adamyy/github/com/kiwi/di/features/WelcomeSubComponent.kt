package adamyy.github.com.kiwi.di.features

import adamyy.github.com.kiwi.ui.features.login.WelcomeFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface WelcomeSubComponent: AndroidInjector<WelcomeFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WelcomeFragment>()

}