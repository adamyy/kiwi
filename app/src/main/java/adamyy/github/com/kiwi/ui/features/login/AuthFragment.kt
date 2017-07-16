package adamyy.github.com.kiwi.ui.features.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import adamyy.github.com.kiwi.data.repository.AuthRepository
import adamyy.github.com.kiwi.data.source.network.TwitterApiConstant
import adamyy.github.com.kiwi.ui.common.SingleUIModel
import adamyy.github.com.kiwi.ui.base.UrlFragment
import android.net.Uri
import android.support.annotation.StringRes
import android.util.Log
import android.webkit.WebView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AuthFragment : UrlFragment() {

    companion object {
        val TAG = AuthFragment::class.simpleName

        fun init(): AuthFragment {
            Log.d(TAG, "init")
            return AuthFragment()
        }
    }

    @Inject lateinit var authRepo: AuthRepository

    override fun onResume() {
        super.onResume()
        loadAuthentication()
    }

    // region UrlFragment

    override fun onOverrideUrlLoading(view: WebView, url: Uri): Boolean {
        if (url.toString().startsWith(TwitterApiConstant.OAUTH_CALLBACK)) {
            handleAuth(url)
            return true
        } else {
            return false
        }
    }

    private fun handleAuth(url: Uri) {
        val denied = url.getQueryParameter(getString(R.string.oauth_denied))
        val verifier = url.getQueryParameter(getString(R.string.oauth_verifier))

        if (denied != null) { // auth denied
            showLoading(false)
            getDelegate().onAuthFailed()
        } else if (verifier != null) { // auth granted
            showLoading(true)
            loadAccessToken(verifier)
        } else { // unknown error
            getDelegate().onAuthFailed()
        }
    }

    override fun onUrlLoaded(url: String?) {
        showLoading(false)
    }

    override fun onLoadingError() {
        getDelegate().onAuthFailed(R.string.sign_in_error_load_url)
    }

    // endregion

    private fun loadAuthentication() {
        authRepo.getOAuthRequestToken()
                .map {
                    (inFlight, isSuccess, data, error) ->
                    if (inFlight) SingleUIModel.inProgress<RequestToken>()
                    else if (isSuccess) SingleUIModel.success(data)
                    else SingleUIModel.failure<RequestToken>(error!!)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (inProgress, isSuccess, result, error) ->
                            if (inProgress) {
                                showLoading(true)
                            } else {
                                if (isSuccess) {
                                    Log.d(TAG, result.toString())
                                    loadUrlWithTimeout(result!!.authenticationUrl)
                                } else {
                                    showLoading(false)
                                    getDelegate().onAuthFailed()
                                }
                            }
                        },
                        { error ->
                            error.printStackTrace()
                            getDelegate().onAuthFailed()
                        },
                        {
                            Log.d(TAG, "request token completed")
                        }
                ).attach()
    }

    private fun loadAccessToken(verifier: String) {
        authRepo.getOAuthAccessToken(verifier)
                .map {
                    (inFlight, isSuccess, data, error) ->
                    if (inFlight) SingleUIModel.inProgress<AccessToken>()
                    else if (isSuccess) SingleUIModel.success(data)
                    else SingleUIModel.failure(error!!)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (inProgress, isSuccess, result, error) ->
                            if (inProgress) {
                                showLoading(true)
                            } else {
                                Log.d(TAG, "AccessToken: $result")
                                if (isSuccess) {
                                    getDelegate().onAuthSuccess()
                                } else {
                                    showLoading(false)
                                    getDelegate().onAuthFailed()
                                }
                            }
                        },
                        { error: Throwable? ->
                            error?.printStackTrace()
                            getDelegate().onAuthFailed()
                        },
                        {
                            Log.d(TAG, "completed")
                        }
                ).attach()
    }

    fun getDelegate(): Delegate = activity as Delegate

    interface Delegate {
        fun onAuthSuccess()
        fun onAuthFailed(@StringRes message: Int = R.string.sign_in_error_generic)
    }
}