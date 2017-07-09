package adamyy.github.com.kiwi.data.source.network

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.RequestToken
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer

interface AuthApi {

    @POST("oauth/request_token")
    fun getRequestToken(@Header("oauth_callback") callback: String = TwitterApiConstant.OAUTH_CALLBACK): Observable<RequestToken>

    @POST("oauth/access_token")
    fun getAccessToken(@Query("oauth_verifier") verifier: String): Observable<AccessToken>

    companion object {
        fun create(): AuthApi {
            val consumer = OkHttpOAuthConsumer(TwitterApiConstant.CONSUMER_KEY, TwitterApiConstant.CONSUMER_SECRET)
            consumer.setTokenWithSecret("", "")

            val okClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(TwitterOAuthInterceptor(consumer))
                    .build()

            return Retrofit.Builder()
                    .baseUrl(TwitterApiConstant.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build().create(AuthApi::class.java)
        }
    }
}