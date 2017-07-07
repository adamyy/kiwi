package adamyy.github.com.kiwi.data.source.preferences

import android.content.Context
import android.content.SharedPreferences
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

class KiwiPreferences(context: Context) : AuthPref {

    companion object {
        val TAG = KiwiPreferences::class.simpleName
        val NAME = "kiwi_preferences"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    // BasePref

    override fun contains(key: String): Boolean = prefs.contains(key)

    // AuthPref

    override fun putRequestToken(requestToken: RequestToken?) = with(prefs.edit()) {
        putString(AuthPref.REQUEST_TOKEN_KEY, requestToken?.token).commit()
        putString(AuthPref.REQUEST_TOKEN_SECRET_KEY, requestToken?.tokenSecret).commit()
    }

    override fun getRequestToken(): RequestToken? = with(prefs) {
        val token = getString(AuthPref.REQUEST_TOKEN_KEY, null)
        val tokenSecret = getString(AuthPref.REQUEST_TOKEN_SECRET_KEY, null)
        return@with if (token != null && tokenSecret != null) RequestToken(token, tokenSecret) else null
    }

    override fun putVerifier(verifier: String?): Boolean = with(prefs.edit()) {
        putString(AuthPref.VERIFIER_KEY, verifier).commit()
    }

    override fun getVerifier(): String? = with(prefs) {
        return@with getString(AuthPref.VERIFIER_KEY, null)
    }

    override fun putAccessToken(accessToken: AccessToken?) = with(prefs.edit()) {
        putString(AuthPref.ACCESS_TOKEN_KEY, accessToken?.token).commit()
        putString(AuthPref.ACCESS_TOKEN_SECRET_KEY, accessToken?.tokenSecret).commit()
    }

    override fun getAccessToken(): AccessToken? = with(prefs) {
        val token = getString(AuthPref.ACCESS_TOKEN_KEY, null)
        val tokenSecret = getString(AuthPref.ACCESS_TOKEN_SECRET_KEY, null)
        return@with if (token != null && tokenSecret != null) AccessToken(token, tokenSecret) else null
    }

}