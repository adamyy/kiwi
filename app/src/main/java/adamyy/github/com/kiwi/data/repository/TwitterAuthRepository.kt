package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.SingleResult
import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import adamyy.github.com.kiwi.data.source.network.AuthApi
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import adamyy.github.com.kiwi.data.source.preferences.KiwiPreferences
import android.content.Context
import io.reactivex.Observable

class TwitterAuthRepository(context: Context) : AuthRepository {

    private val prefs: AuthPref = KiwiPreferences(context.applicationContext)

    private val api: AuthApi = AuthApi.create()

    override fun getOAuthRequestToken(): Observable<SingleResult<RequestToken>> {
        return api.getRequestToken()
                .map {
                    prefs.putRequestToken(it)
                    SingleResult.success(it)
                }
                .startWith(SingleResult.inFlight())
                .onErrorReturn { error -> SingleResult.failure(error.localizedMessage) }
    }

    override fun getOAuthAccessToken(verifier: String): Observable<SingleResult<AccessToken>> {
        return api.getAccessToken(verifier)
                .map {
                    prefs.putAccessToken(it)
                    SingleResult.success(it)
                }
                .startWith(SingleResult.inFlight())
                .onErrorReturn { error -> SingleResult.failure(error.localizedMessage) }
    }
}
