package adamyy.github.com.kiwi.data.source.preferences

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import adamyy.github.com.kiwi.data.fromJson
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class KiwiPreferences(
        private val prefs: SharedPreferences,
        private val gson: Gson
) : AuthPref {

    companion object {
        val TAG = KiwiPreferences::class.simpleName
    }

    // region BasePref

    override fun contains(key: String): Boolean = prefs.contains(key)

    // endregion

    // region AuthPref

    override fun putAccessToken(accessToken: AccessToken?) = with(prefs.edit()) {
        putString(AuthPref.ACCESS_TOKEN_KEY, gson.toJson(accessToken))
        commit()
    }

    override fun getAccessToken(): AccessToken? = with(prefs) {
        val token = getString(AuthPref.ACCESS_TOKEN_KEY, null)
        return@with if(token != null) gson.fromJson(token) else null
    }

    // endregion
}