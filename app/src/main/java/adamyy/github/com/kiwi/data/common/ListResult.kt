package adamyy.github.com.kiwi.data.common

/**
 * A simple wrapper for Result that has a list of data and no other properties
 */
data class ListResult<out T> private constructor(val inFlight: Boolean,
                             val isSuccess: Boolean,
                             val data: List<T>?,
                             val error: String?) : Result() {
    companion object Factory {
        fun <T> inFlight(): ListResult<T> = ListResult(true, false,null, null)
        fun <T> success(result: List<T>): ListResult<T> = ListResult(false, true, result, null)
        fun <T> failure(error: String): ListResult<T> = ListResult(false, false,null, error)
    }
}