package adamyy.github.com.kiwi.data.source.network.auth

import adamyy.github.com.kiwi.BuildConfig
import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @POST("oauth/request_token")
    fun getRequestToken(@Header("oauth_callback") callback: String = BuildConfig.API_CALLBACK): Observable<Response<RequestToken>>

    @POST("oauth/access_token")
    fun getAccessToken(@Query("oauth_verifier") verifier: String): Observable<Response<AccessToken>>
}