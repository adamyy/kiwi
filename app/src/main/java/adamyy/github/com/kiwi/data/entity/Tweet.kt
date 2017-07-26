package adamyy.github.com.kiwi.data.entity

import com.google.gson.annotations.SerializedName

data class Tweet(
        val id: Long,
        val user: User,
        val text: String,
        val truncated: Boolean,
        val createdAt: String,
        val coordinate: Coordinate?,
        val favoriteCount: Int?,
        val favorited: Boolean = false,
        val inReplyToUserId: Long?,
        val inReplyToStatusId: Long?,
        val inReplyToScreenName: String?,
        val quotedStatusId: Long?,
        val quotedStatus: Tweet?,
        val retweetCount: Int,
        val retweeted: Boolean = false,
        val retweetedStatus: Tweet?,
        val place: Place?,
        val entities: Entities
) {
    data class Entities(
            val hashtags: List<HashTag>,
            val symbols: List<Symbol>,
            val urls: List<Url>,
            val userMentions: List<UserMention>,
            val media: List<Media>
    ) {
        data class Media(
                val id: Long,
                // the url of the media file
                @SerializedName("media_url_https")
                val mediaUrl: String,
                // the media url that was extracted
                val url: String,
                // not a url but a string to display
                val displayUrl: String,
                // the fully resolved media url
                val expandedUrl: String,
                // only "photo" for now
                val type: String,
                val indices: List<Int>
        ) {
            fun getThumb() = "$mediaUrl:thumb"
            fun getSmall() = "$mediaUrl:small"
            fun getMedium() = "$mediaUrl:medium"
            fun getLarge() = "$mediaUrl:large"
        }

        data class Url(
                val url: String,
                val displayUrl: String,
                val expandedUrl: String,
                val indices: List<Int>
        )

        data class UserMention(
                val id: Long,
                val screenName: String,
                val name: String,
                val indices: List<Int>
        )

        data class HashTag(
                val text: String,
                val indices: List<Int>
        )

        data class Symbol(
                val text: String,
                val indices: List<Int>
        )
    }
}

