package adamyy.github.com.kiwi.ui.features.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.repository.AuthRepository
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import adamyy.github.com.kiwi.databinding.WelcomeBinding
import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WelcomeFragment : BaseKiwiFragment<WelcomeBinding>() {

    companion object {
        val TAG = WelcomeFragment::class.simpleName
    }

    @Inject lateinit var authRepo: AuthRepository

    override fun getLayoutRes(): Int = R.layout.fragment_welcome

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxView.clicks(binding.buttonSignin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe { getDelegate().startAuth() }
                .attach()
        checkAuth()
    }

    private fun checkAuth() {
        fun showSignin() {
            binding.signinContainer.visibility = View.VISIBLE
        }
        authRepo.isAuthenticated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isAuthenticated ->
                    if (isAuthenticated) {
                        getDelegate().skipAuth()
                    } else {
                        showSignin()
                    }
                }
                .attach()
    }

    fun getDelegate(): Delegate = activity as Delegate

    interface Delegate {
        fun skipAuth()
        fun startAuth()
    }
}