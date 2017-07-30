package adamyy.github.com.kiwi.login.auth

import adamyy.github.com.kiwi.BuildConfig
import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import adamyy.github.com.kiwi.ui.base.UrlFragment
import android.net.Uri
import android.support.annotation.StringRes
import android.util.Log
import android.webkit.WebView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager.OAuthRequestStatus
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager.OAuthAccessStatus

class AuthFragment : UrlFragment() {

    companion object {
        val TAG = AuthFragment::class.simpleName

        fun init(): AuthFragment {
            Log.d(TAG, "init")
            return AuthFragment()
        }
    }

    @Inject lateinit var sessionManager: SessionManager

    override fun onResume() {
        super.onResume()
        loadAuthentication()
    }

    // region UrlFragment

    override fun onOverrideUrlLoading(view: WebView, url: Uri): Boolean {
        if (url.toString().startsWith(BuildConfig.API_CALLBACK)) {
            handleAuth(url)
            return true
        } else {
            return false
        }
    }

    private fun handleAuth(url: Uri) {
        sessionManager.handleAuthCallback(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    state.apply {
                        if (isError) getDelegate().onAuthFailed(info)
                        when (state) {
                            OAuthAccessStatus.POST_ACCESS -> showLoading(true)
                            OAuthAccessStatus.ACCESS_TOKEN_OK -> getDelegate().onAuthSuccess()
                        }
                    }
                }.attach()
    }

    override fun onUrlLoaded(url: String?) {
        showLoading(false)
    }

    override fun onLoadingError() {
        if (isVisible) {
            getDelegate().onAuthFailed(R.string.sign_in_error_load_url)
        }
    }

    // endregion

    private fun loadAuthentication() {
        sessionManager.getAuthRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    when (state) {
                        OAuthRequestStatus.POST_REQUEST -> showLoading(true)
                        OAuthRequestStatus.REQUEST_TOKEN_DENIED -> getDelegate().onAuthFailed(state.info)
                        OAuthRequestStatus.REQUEST_TOKEN_OK -> {
                            showLoading(false)
                            loadUrlWithTimeout(sessionManager.getRequestToken()!!.authenticationUrl)
                        }
                    }
                }.attach()
    }

    fun getDelegate(): Delegate = activity as Delegate

    interface Delegate {
        fun onAuthSuccess()
        fun onAuthFailed(@StringRes message: Int = R.string.sign_in_error_generic)
    }
}