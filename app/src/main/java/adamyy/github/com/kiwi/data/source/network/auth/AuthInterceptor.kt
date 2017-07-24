package adamyy.github.com.kiwi.data.source.network.auth

import oauth.signpost.exception.OAuthException
import okhttp3.*
import org.json.JSONObject
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import java.io.IOException

class AuthInterceptor(val consumer: OkHttpOAuthConsumer) : Interceptor {

    companion object {
        const val OAUTH_PATH = "/oauth"
        const val REQUEST_TOKEN_PATH = "$OAUTH_PATH/request_token"
        const val ACCESS_TOKEN_PATH = "$OAUTH_PATH/access_token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val signedRequest = consumer.sign(chain.request()).unwrap() as Request
            return scanOAuthTokenResponse(chain.proceed(signedRequest))
        } catch (e: OAuthException) {
            throw IOException("Could not sign request", e)
        }
    }

    private fun scanOAuthTokenResponse(response: Response): Response {
        fun scanPlainTextToJson(): Response {
            val body = response.body() ?: return response // if body is null then just return the response
            val responseParams = body.string().split("&")
            val json = JSONObject()
            responseParams.map {
                Pair(it.substringBefore("="), it.substringAfter("="))
            }.forEach { (name, value) ->
                json.put(name, value)
            }
            return response.newBuilder()
                    .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString()))
                    .build()
        }

        val requestPath = response.request().url().encodedPath()
        return if (response.isSuccessful && (requestPath.contains(REQUEST_TOKEN_PATH) || requestPath.contains(ACCESS_TOKEN_PATH))) {
            scanPlainTextToJson()
        } else {
            response
        }
    }

    fun setToken(token: String, tokenSecret: String) {
        consumer.setTokenWithSecret(token, tokenSecret)
    }
}
