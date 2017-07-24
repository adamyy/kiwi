package adamyy.github.com.kiwi.data.source.network

import adamyy.github.com.kiwi.data.entity.AccessToken
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import oauth.signpost.exception.OAuthException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import java.io.IOException

class SigningInterceptor(private val sessionManager: SessionManager,
                         private val consumer: OkHttpOAuthConsumer
): Interceptor {

    companion object {
        const val UNAUTHORIZED = 401 // authorization has not been provided
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        fun signRequest(request: Request): Request {
            consumer.setAccessToken(sessionManager.getSession())
            return consumer.sign(request).unwrap() as Request
        }

        fun scanResponse(response: Response): Response {
            when (response.code()) {
                UNAUTHORIZED -> sessionManager.onRequestUnauthorized()
            }
            return response
        }

        try {
            return scanResponse(chain.proceed(signRequest(chain.request())))
        } catch (e: OAuthException) {
            throw IOException("Could not sign the request", e)
        }
    }

    fun OkHttpOAuthConsumer.setAccessToken(accessToken: AccessToken?) {
        setTokenWithSecret(accessToken?.token, accessToken?.tokenSecret)
    }
}