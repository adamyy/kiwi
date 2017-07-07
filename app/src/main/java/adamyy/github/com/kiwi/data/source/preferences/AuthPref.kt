package adamyy.github.com.kiwi.data.source.preferences

import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

interface AuthPref : BasePref{

    companion object {
        const val REQUEST_TOKEN_KEY: String = "REQUEST_TOKEN"
        const val REQUEST_TOKEN_SECRET_KEY: String = "REQUEST_TOKEN_SECRET"
        const val VERIFIER_KEY: String = "VERIFIER"
        const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN"
        const val ACCESS_TOKEN_SECRET_KEY: String = "ACCESS_TOKEN_SECRET"
    }

    fun putRequestToken(requestToken: RequestToken?): Boolean

    fun getRequestToken(): RequestToken?

    fun putVerifier(verifier: String?): Boolean

    fun getVerifier(): String?

    fun putAccessToken(accessToken: AccessToken?): Boolean

    fun getAccessToken(): AccessToken?

}