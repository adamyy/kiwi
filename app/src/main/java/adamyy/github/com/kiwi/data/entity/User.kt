package adamyy.github.com.kiwi.data.entity

import com.google.gson.annotations.SerializedName

/**
 * https://dev.twitter.com/overview/api/users
 */
data class User(
        val id: Long,

        /**
         * The name of the user, as they’ve defined it. Not necessarily a person’s name.
         */
        val name: String,

        /**
         * The screen name, handle, or alias that this user identifies themselves with.
         * screen_names are unique but subject to change.
         * Use id_str as a user identifier whenever possible.
         */
        val screenName: String,

        /**
         * Nullable. The user-defined location for this account’s profile. Not necessarily a location, nor machine-parseable.
         */
        val location: String?,

        /**
         * Nullable. The user-defined UTF-8 string describing their account.
         */
        val description: String?,

        /**
         * Nullable. A URL provided by the user in association with their profile.
         */
        val url: String?,

        /**
         * When true, indicates that this user has chosen to protect their Tweets.
         */
        @SerializedName("protected") val protectTweets: Boolean,

        /**
         * The number of followers this account currently has.
         */
        val followersCount: Int,

        /**
         * The number of users this account is following (AKA their “followings”).
         */
        val friendsCount: Int,

        /**
         * The number of public lists that this user is a member of.
         */
        val listedCount: Int,

        /**
         * The UTC datetime that the user account was created on Twitter.
         */
        val createdAt: String,

        /**
         * The number of Tweets this user has liked in the account’s lifetime.
         */
        val favouritesCount: Int,

        /**
         * Nullable. A string describing the Time Zone this user declares themselves within.
         */
        val timeZone: String?,

        /**
         * When true, indicates that the user has enabled the possibility of geotagging their Tweets.
         * This field must be true for the current user to attach geographic data when using POST statuses / update.
         */
        val geoEnabled: Boolean,

        /**
         * When true, indicates that the user has a verified account.
         */
        val verified: Boolean,

        /**
         * The number of Tweets (including retweets) issued by the user.
         */
        val statusesCount: Int,

        /**
         * The BCP 47 code for the user’s self-declared user interface language.
         */
        val lang: String,

        /**
         * A HTTPS-based URL pointing to the background image the user has uploaded for their profile.
         */
        @SerializedName("profile_background_image_url_https") val profileBackgroundImageUrl: String,

        /**
         * A HTTPS-based URL pointing to the user’s profile image.
         */
        @SerializedName("profile_image_url_https") val profileImageUrl: String,

        /**
         * The HTTPS-based URL pointing to the standard web representation of the user’s uploaded profile banner.
         */
        @SerializedName("profile_banner_url") val profileBannerUrl: String,

        /**
         * When true, indicates that the user has not uploaded their own profile image and a default image is used instead.
         */
        @SerializedName("default_profile_image") val useDefaultProfileImage: Boolean
)