package adamyy.github.com.kiwi.data.source.network.auth

import adamyy.github.com.kiwi.KiwiApplication
import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import adamyy.github.com.kiwi.data.entity.User
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import android.net.Uri
import android.support.annotation.StringRes
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer

class SessionManager(private val authPref: AuthPref,
                     private val application: KiwiApplication,
                     apiKey: String,
                     apiSecret: String,
                     authUrl: String) {

    companion object {
        const val OAUTH_DENIED = "oauth_denied"
        const val OAUTH_VERIFIER = "oauth_verifier"
        const val OAUTH_TOKEN = "oauth_token"
    }

    enum class OAuthRequestStatus(@StringRes val info: Int, val isError: Boolean) {
        POST_REQUEST(R.string.state_post_request, false),
        REQUEST_TOKEN_DENIED(R.string.state_request_token_denied, true),
        REQUEST_TOKEN_OK(R.string.state_request_token_ok, false)
    }

    enum class OAuthAccessStatus(@StringRes val info: Int, val isError: Boolean) {
        VERIFIER_DENIED(R.string.state_access_token_denied, true),
        POST_ACCESS(R.string.state_post_access, false),
        ACCESS_TOKEN_DENIED(R.string.state_access_token_denied, true),
        ACCESS_TOKEN_OK(R.string.state_access_token_ok, false),
        UNKNOWN_ERROR(R.string.unknown_error, true)
    }

    private var requestToken: RequestToken? = null
    private var accessToken: AccessToken? = null
    private var currentUser: User? = null

    private val authInterceptor by lazy {
        AuthInterceptor(OkHttpOAuthConsumer(apiKey, apiSecret))
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(authInterceptor)
                .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(authUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    private val authApi by lazy { retrofit.create(AuthApi::class.java) }

    init {
        loadStoredSession()
    }

    private fun loadStoredSession() {
        val token = authPref.getAccessToken()
        if (token != null) {
            startSession(token)
        } else {
            clearSession()
        }
    }

    private fun startSession(accessToken: AccessToken) {
        this.accessToken = accessToken
        authPref.putAccessToken(accessToken)
    }

    private fun clearSession() {
        accessToken = null
        authPref.putAccessToken(null)
    }

    private fun startRequest(requestToken: RequestToken) {
        this.requestToken = requestToken
        authInterceptor.setToken(requestToken.token, requestToken.tokenSecret)
    }

    private fun clearRequest() {
        this.requestToken = null
        authInterceptor.setToken("", "")
    }

    fun getAuthRequest(): Observable<OAuthRequestStatus> {
        clearRequest()
        return authApi.getRequestToken()
                .flatMap { response ->
                    val requestToken = response.body()
                    if (response.isSuccessful && requestToken != null) {
                        startRequest(requestToken)
                        Observable.just(OAuthRequestStatus.REQUEST_TOKEN_OK)
                    } else {
                        Observable.just(OAuthRequestStatus.REQUEST_TOKEN_DENIED)
                    }
                }
                .startWith(Observable.just(OAuthRequestStatus.POST_REQUEST))
                .onErrorReturn { OAuthRequestStatus.REQUEST_TOKEN_DENIED }
    }

    fun handleAuthCallback(url: Uri): Observable<OAuthAccessStatus> {
        val denied = url.getQueryParameter(OAUTH_DENIED)
        val verifier = url.getQueryParameter(OAUTH_VERIFIER)

        return if (verifier != null) { // auth granted
            authApi.getAccessToken(verifier)
                    .flatMap { response ->
                        val accessToken = response.body()
                        if (response.isSuccessful && accessToken != null) {
                            startSession(accessToken)
                            Observable.just(OAuthAccessStatus.ACCESS_TOKEN_OK)
                        } else {
                            clearRequest()
                            Observable.just(OAuthAccessStatus.ACCESS_TOKEN_DENIED)
                        }
                    }
                    .startWith(Observable.just(OAuthAccessStatus.POST_ACCESS))
                    .onErrorReturn {
                        clearRequest()
                        OAuthAccessStatus.ACCESS_TOKEN_DENIED
                    }
        } else {
            clearRequest()
            if (denied != null) { // auth denied
                Observable.just(OAuthAccessStatus.VERIFIER_DENIED)
            } else { // unknown error
                Observable.just(OAuthAccessStatus.UNKNOWN_ERROR)
            }
        }
    }

    fun onRequestUnauthorized() {
        abortAuthentication()
    }

    fun abortAuthentication() {
        clearRequest()
        clearSession()
    }

    fun isAuthenticated(): Boolean {
        loadStoredSession()
        return accessToken != null
    }

    fun getRequestToken(): RequestToken? = requestToken

    fun getSession(): AccessToken? = accessToken
}
