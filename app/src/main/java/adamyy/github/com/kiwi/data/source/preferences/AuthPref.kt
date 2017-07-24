package adamyy.github.com.kiwi.data.source.preferences

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken

interface AuthPref : BasePref {

    companion object {
        const val ACCESS_TOKEN_KEY: String = "ACCESS_TOKEN"
    }

    fun putAccessToken(accessToken: AccessToken?): Boolean

    fun getAccessToken(): AccessToken?

}