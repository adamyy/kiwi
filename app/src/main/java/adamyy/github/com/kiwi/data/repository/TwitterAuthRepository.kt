package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.BuildConfig
import adamyy.github.com.kiwi.data.common.SingleResult
import adamyy.github.com.kiwi.data.source.preferences.AuthPref
import adamyy.github.com.kiwi.data.source.preferences.KiwiPreferences
import android.content.Context
import io.reactivex.Observable
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

class TwitterAuthRepository(context: Context) : AuthRepository {

    private val prefs: AuthPref = KiwiPreferences(context.applicationContext)

    private val twitterInstance: Twitter by lazy {
        val factory = TwitterFactory(config)
        factory.instance
    }

    private val config: Configuration = ConfigurationBuilder()
            .setOAuthConsumerKey(BuildConfig.API_KEY)
            .setOAuthConsumerSecret(BuildConfig.API_SECRET)
            .build()

    override fun getOAuthRequestToken(): Observable<SingleResult<RequestToken>> {
        val result: Observable<SingleResult<RequestToken>> = Observable.create { subscriber ->
            val requestToken = twitterInstance.getOAuthRequestToken(BuildConfig.API_CALLBACK)
            prefs.putRequestToken(requestToken)
            subscriber.onNext(SingleResult.success(requestToken))
            subscriber.onComplete()
        }
        return result.startWith(SingleResult.inFlight())
                .onErrorReturn { error -> SingleResult.failure(error.localizedMessage) }
    }

    override fun getOAuthAccessToken(): Observable<SingleResult<AccessToken>> {
        val result: Observable<SingleResult<AccessToken>> = Observable.create { subscriber ->
            val requestToken = prefs.getRequestToken()
            val verifier = prefs.getVerifier()
            val accessToken = twitterInstance.getOAuthAccessToken(requestToken, verifier)
            subscriber.onNext(SingleResult.success(accessToken))
            subscriber.onComplete()
        }
        return result.startWith(SingleResult.inFlight())
                .onErrorReturn { error -> SingleResult.failure(error.localizedMessage) }
    }
}
