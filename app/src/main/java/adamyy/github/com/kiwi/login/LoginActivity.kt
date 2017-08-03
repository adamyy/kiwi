package adamyy.github.com.kiwi.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.base.BaseKiwiActivity
import adamyy.github.com.kiwi.databinding.BasicBinding
import adamyy.github.com.kiwi.login.auth.AuthFragment
import adamyy.github.com.kiwi.login.welcome.WelcomeFragment
import adamyy.github.com.kiwi.ui.common.action
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentTransaction
import com.yifan.butterfly.BActivity
import com.yifan.butterfly.Butterfly

@BActivity
class LoginActivity : BaseKiwiActivity<LoginContract.View, LoginContract.Presenter, BasicBinding>(),
        LoginContract.View, WelcomeFragment.Delegate, AuthFragment.Delegate {

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun showWelcome() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, WelcomeFragment(), WelcomeFragment.TAG)
                .commit()
    }

    override fun transitToHome() {
        Butterfly.toHomeActivity().go(this)
        finish()
    }

    // region WelcomeFragment#Delegate

    override fun startAuth() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, AuthFragment(), AuthFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(AuthFragment.TAG)
                .commit()
    }

    // endregion

    // region AuthFragment#Delegate

    override fun onAuthSuccess() {
        transitToHome()
    }

    override fun onAuthFailed(message: Int) {
        supportFragmentManager.popBackStack()
        snackMessage(message, length = Snackbar.LENGTH_LONG) {
            action(R.string.retry, R.color.failure) { startAuth() }
        }
    }

    override fun pressBack() {
        onBackPressed()
    }

    // endregion

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
