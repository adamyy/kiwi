package adamyy.github.com.kiwi.login.welcome

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface WelcomeSubComponent: AndroidInjector<WelcomeFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WelcomeFragment>()

}