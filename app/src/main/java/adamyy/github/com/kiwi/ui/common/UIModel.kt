package adamyy.github.com.kiwi.ui.common

abstract class UIModel

data class SingleUIModel<out T> private constructor(val inFlight: Boolean,
                                                    val isSuccess: Boolean,
                                                    val result: T?,
                                                    val error: String?): UIModel() {
    companion object Factory {
        fun <T> inProgress(): SingleUIModel<T> = SingleUIModel(true, false, null, null)
        fun <T> success(result: T): SingleUIModel<T> = SingleUIModel(false, true, result, null)
        fun <T> failure(error: String): SingleUIModel<T> = SingleUIModel(false, false, null, error)
    }
}
