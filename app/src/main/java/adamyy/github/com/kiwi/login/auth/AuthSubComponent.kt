package adamyy.github.com.kiwi.login.auth

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface AuthSubComponent : AndroidInjector<AuthFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AuthFragment>()

}