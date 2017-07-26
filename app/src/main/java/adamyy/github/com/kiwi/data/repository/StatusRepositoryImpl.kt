package adamyy.github.com.kiwi.data.repository

import adamyy.github.com.kiwi.data.common.AsyncResult
import adamyy.github.com.kiwi.data.common.wrapInAsyncResult
import adamyy.github.com.kiwi.data.entity.Tweet
import adamyy.github.com.kiwi.data.source.network.StatusApi
import io.reactivex.Observable

class StatusRepositoryImpl(private val statusApi: StatusApi): StatusRepository {

    override fun getHomeTimeline(): Observable<AsyncResult<List<Tweet>>> = statusApi.getHomeTimeline().wrapInAsyncResult()

}