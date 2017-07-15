package adamyy.github.com.kiwi.data.source.network

import oauth.signpost.exception.OAuthException
import okhttp3.*
import org.json.JSONObject
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import java.io.IOException

class TwitterOAuthInterceptor(val consumer: OkHttpOAuthConsumer) : Interceptor {

    companion object {
        const val OAUTH_PATH = "/oauth"
        const val REQUEST_TOKEN_PATH = "/oauth/request_token"
        const val ACCESS_TOKEN_PATH = "/oauth/access_token"
        const val PARAM_TOKEN = "oauth_token"
        const val PARAM_SECRET = "oauth_token_secret"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val signedRequest = consumer.sign(chain.request()).unwrap() as Request
            return scanOAuthTokenResponse(chain.proceed(scanRequest(signedRequest)))
        } catch (e: OAuthException) {
            throw IOException("Could not sign request", e)
        }
    }

    private fun scanRequest(request: Request): Request {
        val url = request.url()
        val encodedPath = url.encodedPath()
        return if (!encodedPath.startsWith(OAUTH_PATH)) {
            request.newBuilder()
                    .url(url.newBuilder().encodedPath("/${TwitterApiConstant.API_VERSION}$encodedPath").build())
                    .build()
        } else {
            request
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
                if (name == PARAM_TOKEN) {
                    consumer.setTokenWithSecret(value, consumer.tokenSecret)
                } else if (name == PARAM_SECRET) {
                    consumer.setTokenWithSecret(consumer.token, value)
                }
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

}
