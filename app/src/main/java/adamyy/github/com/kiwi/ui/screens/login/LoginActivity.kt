package adamyy.github.com.kiwi.ui.screens.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.screens.base.BaseKiwiActivity
import adamyy.github.com.kiwi.databinding.BasicBinding
import adamyy.github.com.kiwi.ui.common.action
import adamyy.github.com.kiwi.ui.screens.home.HomeActivity
import android.content.Intent
import android.support.design.widget.Snackbar

class LoginActivity : BaseKiwiActivity<BasicBinding>(), WelcomeFragment.Delegate, AuthFragment.Delegate {

    companion object {
        val TAG = LoginActivity::class.simpleName
    }

    override fun initUi() {
        val existingFragment = supportFragmentManager.findFragmentByTag(WelcomeFragment.TAG)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, existingFragment ?: WelcomeFragment(), WelcomeFragment.TAG)
                .commit()
    }

    // region WelcomeFragment#Delegate

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
        supportFragmentManager.popBackStack()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
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

}
