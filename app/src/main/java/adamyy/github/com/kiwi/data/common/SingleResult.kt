package adamyy.github.com.kiwi.data.common

/**
 * A simple wrapper for Result that has a single value of data and no other properties
 */
data class SingleResult<out T> private constructor(val inFlight: Boolean,
                                val isSuccess: Boolean,
                                val data: T?,
                                val error: String?): Result() {
    companion object Factory {
        fun <T> inFlight(): SingleResult<T> = SingleResult(true, false, null, null)
        fun <T> success(result: T): SingleResult<T> = SingleResult(false, true, result, null)
        fun <T> failure(error: String): SingleResult<T> = SingleResult(false, false,null, error)
    }
}