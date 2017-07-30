package adamyy.github.com.kiwi.login

import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import adamyy.github.com.kiwi.login.auth.AuthSubComponent
import adamyy.github.com.kiwi.login.welcome.WelcomeSubComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = arrayOf(
        AuthSubComponent::class,
        WelcomeSubComponent::class
))
class LoginModule {

    @Provides
    fun provideLoginPresenter(sessionManager: SessionManager): LoginContract.Presenter {
        return LoginPresenter(sessionManager)
    }

}