package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.SingleResult
import adamyy.github.com.kiwi.data.common.wrapInSingleResult
import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import adamyy.github.com.kiwi.data.source.network.AuthApi
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import io.reactivex.Observable

class AuthRepositoryImpl(val prefs: AuthPref, val authApi: AuthApi) : AuthRepository {

    override fun getOAuthRequestToken(): Observable<SingleResult<RequestToken>> =
            authApi.getRequestToken().wrapInSingleResult { prefs.putRequestToken(it) }

    override fun getOAuthAccessToken(verifier: String): Observable<SingleResult<AccessToken>> =
            authApi.getAccessToken(verifier).wrapInSingleResult { prefs.putAccessToken(it) }
}
