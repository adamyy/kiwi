package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.AsyncResult
import adamyy.github.com.kiwi.data.entity.Tweet
import io.reactivex.Observable

interface StatusRepository {

    fun getHomeTimeline(): Observable<AsyncResult<List<Tweet>>>

}