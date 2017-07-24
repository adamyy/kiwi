package adamyy.github.com.kiwi.data.common

import io.reactivex.Observable

abstract class Result

/**
 * A simple wrapper for Result that has a single value of data and no other properties
 */
data class AsyncResult<out T> private constructor(val inFlight: Boolean,
                                                  val isSuccess: Boolean,
                                                  val data: T?,
                                                  val error: String?) : Result() {
    companion object Factory {
        fun <T> inFlight(): AsyncResult<T> = AsyncResult(true, false, null, null)
        fun <T> success(result: T): AsyncResult<T> = AsyncResult(false, true, result, null)
        fun <T> failure(error: String): AsyncResult<T> = AsyncResult(false, false, null, error)
    }
}

fun <T> Observable<T>.wrapInAsyncResult(onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit = {}): Observable<AsyncResult<T>> {
    return this.map {
        onSuccess(it)
        AsyncResult.success(it)
    }.startWith(AsyncResult.inFlight()).onErrorReturn {
        onError(it)
        AsyncResult.failure(it.localizedMessage)
    }
}