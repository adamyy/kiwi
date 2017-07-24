package adamyy.github.com.kiwi.di.features

import adamyy.github.com.kiwi.login.AuthFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface AuthSubComponent : AndroidInjector<AuthFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AuthFragment>()

}