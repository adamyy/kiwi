package adamyy.github.com.kiwi.login

import adamyy.github.com.kiwi.ui.base.BasePresenter
import adamyy.github.com.kiwi.ui.base.BaseView

interface LoginContract {

    interface Presenter: BasePresenter<View> {

    }

    interface View: BaseView {
        fun showWelcome()
        fun transitToHome()
    }

}