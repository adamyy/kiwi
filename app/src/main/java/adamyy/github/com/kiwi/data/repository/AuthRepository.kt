package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.SingleResult
import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import io.reactivex.Observable

interface AuthRepository {

    fun getOAuthRequestToken(): Observable<SingleResult<RequestToken>>

    fun getOAuthAccessToken(verifier: String): Observable<SingleResult<AccessToken>>
}