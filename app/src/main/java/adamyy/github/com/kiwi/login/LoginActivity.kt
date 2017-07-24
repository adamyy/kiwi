package adamyy.github.com.kiwi.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.base.BaseKiwiActivity
import adamyy.github.com.kiwi.databinding.BasicBinding
import adamyy.github.com.kiwi.ui.common.action
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.yifan.butterfly.BActivity
import com.yifan.butterfly.Butterfly

@BActivity
class LoginActivity : BaseKiwiActivity<BasicBinding>(), WelcomeFragment.Delegate, AuthFragment.Delegate {

    companion object {
        val TAG = LoginActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWelcome()
    }

    private fun showWelcome() {
        val existingFragment = supportFragmentManager.findFragmentByTag(WelcomeFragment.TAG)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, existingFragment ?: WelcomeFragment(), WelcomeFragment.TAG)
                .commit()
    }

    // region WelcomeFragment#Delegate

    override fun skipAuth() {
        transitToHome()
    }

    override fun startAuth() {
        val authFragment = AuthFragment.init()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, authFragment, AuthFragment.TAG)
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

    // endregion

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun transitToHome() {
        Butterfly.toHomeActivity().go(this)
        finish()
    }
}
