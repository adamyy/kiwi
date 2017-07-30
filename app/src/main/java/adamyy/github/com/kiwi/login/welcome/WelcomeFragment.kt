package adamyy.github.com.kiwi.login.welcome

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import adamyy.github.com.kiwi.databinding.WelcomeBinding
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WelcomeFragment : BaseKiwiFragment<WelcomeBinding>() {

    companion object {
        val TAG = WelcomeFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_welcome

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxView.clicks(binding.buttonSignin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { getDelegate().startAuth() }
                .attach()
    }

    fun getDelegate(): Delegate = activity as Delegate

    interface Delegate {
        fun startAuth()
    }
}