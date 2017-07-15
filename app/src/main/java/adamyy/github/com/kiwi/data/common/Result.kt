package adamyy.github.com.kiwi.data.common

import io.reactivex.Observable

abstract class Result

/**
 * A simple wrapper for Result that has a single value of data and no other properties
 */
data class SingleResult<out T> private constructor(val inFlight: Boolean,
                                                   val isSuccess: Boolean,
                                                   val data: T?,
                                                   val error: String?) : Result() {
    companion object Factory {
        fun <T> inFlight(): SingleResult<T> = SingleResult(true, false, null, null)
        fun <T> success(result: T): SingleResult<T> = SingleResult(false, true, result, null)
        fun <T> failure(error: String): SingleResult<T> = SingleResult(false, false, null, error)
    }
}

fun <T> Observable<T>.wrapInSingleResult(onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit = {}): Observable<SingleResult<T>> {
    return this.map {
        onSuccess(it)
        SingleResult.success(it)
    }.startWith(SingleResult.inFlight()).onErrorReturn {
        onError(it)
        SingleResult.failure(it.localizedMessage)
    }
}

/**
 * A simple wrapper for Result that has a list of data and no other properties
 */
data class ListResult<out T> private constructor(val inFlight: Boolean,
                                                 val isSuccess: Boolean,
                                                 val data: List<T>?,
                                                 val error: String?) : Result() {
    companion object Factory {
        fun <T> inFlight(): ListResult<T> = ListResult(true, false, null, null)
        fun <T> success(result: List<T>): ListResult<T> = ListResult(false, true, result, null)
        fun <T> failure(error: String): ListResult<T> = ListResult(false, false, null, error)
    }
}