package adamyy.github.com.kiwi.login.auth

import adamyy.github.com.kiwi.BuildConfig
import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import adamyy.github.com.kiwi.ui.base.UrlFragment
import android.net.Uri
import android.view.MenuItem
import android.support.annotation.StringRes
import android.webkit.WebView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager.OAuthRequestStatus
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager.OAuthAccessStatus

class AuthFragment : UrlFragment() {

    companion object {
        const val TAG = "AuthFragment"
    }

    @Inject lateinit var sessionManager: SessionManager

    override fun onResume() {
        super.onResume()
        loadAuthentication()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                getDelegate()?.pressBack()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
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
                        if (isError) getDelegate()?.onAuthFailed(info)
                        when (state) {
                            OAuthAccessStatus.POST_ACCESS -> showLoading(true)
                            OAuthAccessStatus.ACCESS_TOKEN_OK -> getDelegate()?.onAuthSuccess()
                        }
                    }
                }.attach()
    }

    override fun onUrlLoaded(url: String?) {
        setToolbarTitle(R.string.sign_in_with_twitter)
        showLoading(false)
    }

    override fun onLoadingError() {
        getDelegate()?.onAuthFailed(R.string.sign_in_error_load_url)
    }

    // endregion

    private fun loadAuthentication() {
        setToolbarTitle(R.string.loading)
        sessionManager.getAuthRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    when (state) {
                        OAuthRequestStatus.POST_REQUEST -> showLoading(true)
                        OAuthRequestStatus.REQUEST_TOKEN_DENIED -> getDelegate()?.onAuthFailed(state.info)
                        OAuthRequestStatus.REQUEST_TOKEN_OK -> {
                            showLoading(false)
                            loadUrlWithTimeout(sessionManager.getRequestToken()!!.authenticationUrl)
                        }
                    }
                }.attach()
    }

    private fun setToolbarTitle(@StringRes title: Int) {
        binding.toolbarTitle.setText(title)
    }

    fun getDelegate(): Delegate? = activity as Delegate?

    interface Delegate {
        fun onAuthSuccess()
        fun onAuthFailed(@StringRes message: Int = R.string.sign_in_error_generic)
        fun pressBack()
    }
}