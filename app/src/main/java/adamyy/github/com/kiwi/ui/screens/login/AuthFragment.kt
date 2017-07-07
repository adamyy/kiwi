package adamyy.github.com.kiwi.ui.screens.login

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import adamyy.github.com.kiwi.data.source.preferences.KiwiPreferences
import adamyy.github.com.kiwi.data.repository.AuthRepository
import adamyy.github.com.kiwi.data.repository.AuthenticationUrl
import adamyy.github.com.kiwi.data.repository.TwitterAuthRepository
import adamyy.github.com.kiwi.ui.common.SingleUIModel
import adamyy.github.com.kiwi.ui.screens.base.UrlFragment
import android.net.Uri
import android.support.annotation.StringRes
import android.util.Log
import android.webkit.WebView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import twitter4j.auth.AccessToken

class AuthFragment : UrlFragment() {

    companion object {
        val TAG = AuthFragment::class.simpleName

        fun init(): AuthFragment {
            Log.d(TAG, "init")
            return AuthFragment()
        }
    }

    val authRepo: AuthRepository by lazy { TwitterAuthRepository(context) }
    val prefs: AuthPref by lazy { KiwiPreferences(context) }

    override fun initUi() {
        super.initUi()
        loadAuthenticationUrl()
    }

    // region UrlFragment

    override fun onOverrideUrlLoading(view: WebView, url: Uri): Boolean {
        if (url.toString().startsWith(getString(R.string.twitter_api_callback_url))) {
            showLoading(true)
            prefs.putVerifier(url.getQueryParameter(getString(R.string.oauth_verifier)))
            loadAccessToken()
            return true
        } else {
            return false
        }
    }

    override fun onUrlLoaded(url: String?) {
        showLoading(false)
    }

    override fun onLoadingError() {
        activity.supportFragmentManager.beginTransaction().remove(this).commit()
        getDelegate().onAuthFailed(R.string.sign_in_error_load_url)
    }

    // endregion

    private fun loadAuthenticationUrl() {
        addDisposable(authRepo.getOAuthRequestToken()
                .map {
                    (inFlight, isSuccess, data, error) ->
                    if (inFlight) SingleUIModel.inProgress<AuthenticationUrl>()
                    else if (isSuccess) SingleUIModel.success(data!!.authenticationURL)
                    else SingleUIModel.failure<AuthenticationUrl>(error!!)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { (inProgress, isSuccess, result, error) ->
                            if (inProgress) {
                                showLoading(true)
                            } else {
                                if (isSuccess) {
                                    loadUrlWithTimeout(result!!)
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
                ))
    }

    private fun loadAccessToken() {
        addDisposable(authRepo.getOAuthAccessToken()
                .map {
                    (inFlight, isSuccess, data, error) ->
                    if (inFlight) SingleUIModel.inProgress<AccessToken>()
                    else if (isSuccess) SingleUIModel.success(data!!)
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
                                    prefs.putAccessToken(result)
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
                )
        )
    }

    fun getDelegate(): Delegate = activity as Delegate

    interface Delegate {
        fun onAuthSuccess()
        fun onAuthFailed(@StringRes message: Int = R.string.sign_in_error_generic)
    }
}