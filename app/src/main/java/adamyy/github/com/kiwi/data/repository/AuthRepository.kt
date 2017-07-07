package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.SingleResult
import io.reactivex.Observable
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken

interface AuthRepository {

    fun getOAuthRequestToken(): Observable<SingleResult<RequestToken>>

    fun getOAuthAccessToken(): Observable<SingleResult<AccessToken>>
}

typealias AuthenticationUrl = String