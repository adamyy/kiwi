package adamyy.github.com.kiwi.data.source.network

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.entity.User
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer

interface UserApi {

    @GET("users/show.json")
    fun getUserById(
            @Query("user_id") userId: String,
            @Query("include_entities") includeEntities: Boolean = true
    ): Observable<User>

    @GET("users/show.json")
    fun getUserByName(
            @Query("screen_name") screenName: String,
            @Query("include_entities") includeEntities: Boolean = true
    ): Observable<User>

    @GET("account/verify_credentials.json")
    fun verifyCredentials(
            @Query("include_entities") includeEntities: Boolean = false,
            @Query("skip_status") skipStatus: Boolean = true,
            @Query("include_email") includeEmail: Boolean = true
    ): Observable<User>

    companion object {
        fun create(accessToken: AccessToken): UserApi {
            val consumer = OkHttpOAuthConsumer(TwitterApiConstant.CONSUMER_KEY, TwitterApiConstant.CONSUMER_SECRET)
            consumer.setTokenWithSecret(accessToken.token, accessToken.tokenSecret)

            val okClient = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor())
                    .addInterceptor(TwitterOAuthInterceptor(consumer))
                    .build()

            return Retrofit.Builder()
                    .baseUrl(TwitterApiConstant.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build().create(UserApi::class.java)
        }
    }

}