package adamyy.github.com.kiwi.ui.screens.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.screens.base.BaseKiwiFragment
import adamyy.github.com.kiwi.databinding.WelcomeBinding
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

class WelcomeFragment : BaseKiwiFragment<WelcomeBinding>() {

    companion object {
        val TAG = WelcomeFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_welcome

    override fun initUi() {
        RxView.clicks(binding.buttonSignin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe{getDelegate().startAuth()}
    }

    fun getDelegate(): Delegate = activity as Delegate

    interface Delegate {
        fun startAuth()
    }
}