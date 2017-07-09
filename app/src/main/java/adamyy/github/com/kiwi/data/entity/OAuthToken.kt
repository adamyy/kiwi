package adamyy.github.com.kiwi.data.entity

import com.google.gson.annotations.SerializedName

data class RequestToken(
        @SerializedName("oauth_token") val token: String,
        @SerializedName("oauth_token_secret") val tokenSecret: String
) {
    val authenticationUrl: String get() = "https://api.twitter.com/oauth/authenticate?oauth_token=$token"
    val authorizationUrl: String get() = "https://api.twitter.com/oauth/authorize?oauth_token=$token"
}

data class AccessToken(
        @SerializedName("oauth_token") val token: String,
        @SerializedName("oauth_token_secret") val tokenSecret: String,
        @SerializedName("user_id") val userId: String,
        @SerializedName("screen_name") val screenName: String
)