package adamyy.github.com.kiwi.data.source.preferences

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken

interface AuthPref : BasePref {

    companion object {
        const val REQUEST_TOKEN_KEY: String = "REQUEST_TOKEN"
        const val VERIFIER_KEY: String = "VERIFIER"
        const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN"
    }

    fun putRequestToken(requestToken: RequestToken?): Boolean

    fun getRequestToken(): RequestToken?

    fun putVerifier(verifier: String): Boolean

    fun getVerifier(): String

    fun putAccessToken(accessToken: AccessToken?): Boolean

    fun getAccessToken(): AccessToken?

}