package adamyy.github.com.kiwi.login

import adamyy.github.com.kiwi.data.source.network.auth.SessionManager

class LoginPresenter(private val sessionManager: SessionManager) : LoginContract.Presenter {

    private lateinit var view: LoginContract.View

    override fun bindView(view: LoginContract.View) {
        this.view = view
    }

    override fun start() {
        if (sessionManager.isAuthenticated()) {
            view.transitToHome()
        } else {
            view.showWelcome()
        }
    }

}