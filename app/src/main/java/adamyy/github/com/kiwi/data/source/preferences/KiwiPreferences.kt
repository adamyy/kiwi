package adamyy.github.com.kiwi.data.source.preferences

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import adamyy.github.com.kiwi.data.fromJson
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class KiwiPreferences(context: Context) : AuthPref {

    companion object {
        val TAG = KiwiPreferences::class.simpleName
        val NAME = "kiwi_preferences"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    private val gson: Gson by lazy { Gson() }

    // BasePref

    override fun contains(key: String): Boolean = prefs.contains(key)

    // AuthPref

    override fun putRequestToken(requestToken: RequestToken?) = with(prefs.edit()) {
        putString(AuthPref.REQUEST_TOKEN_KEY, gson.toJson(requestToken))
        commit()
    }

    override fun getRequestToken(): RequestToken? = with(prefs) {
        val token = getString(AuthPref.REQUEST_TOKEN_KEY, null)
        return@with gson.fromJson(token)
    }

    override fun putVerifier(verifier: String): Boolean = with(prefs.edit()) {
        putString(AuthPref.VERIFIER_KEY, verifier)
        commit()
    }

    override fun getVerifier(): String = with(prefs) {
        return@with getString(AuthPref.VERIFIER_KEY, "")
    }

    override fun putAccessToken(accessToken: AccessToken?) = with(prefs.edit()) {
        putString(AuthPref.ACCESS_TOKEN_KEY, gson.toJson(accessToken))
        commit()
    }

    override fun getAccessToken(): AccessToken? = with(prefs) {
        val token = getString(AuthPref.ACCESS_TOKEN_KEY, null)
        return@with gson.fromJson(token)
    }

}