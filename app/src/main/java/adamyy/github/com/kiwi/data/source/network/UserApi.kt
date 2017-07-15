package adamyy.github.com.kiwi.data.source.network

import adamyy.github.com.kiwi.data.entity.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

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
}