package adamyy.github.com.kiwi.data.source.network

import adamyy.github.com.kiwi.data.entity.Tweet
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StatusApi {

    @GET("statuses/home_timeline")
    fun getHomeTimeline(
            @Query("count") count: Int = 20,
            @Query("since_id") sinceId: Long? = null,
            @Query("max_id") maxId: Long? = null,
            @Query("trim_user") trimUser: Boolean = false,
            @Query("exclude_replies") excludeReplies: Boolean = false,
            @Query("include_entities") includeEntities: Boolean = false
    ): Observable<List<Tweet>>

}
