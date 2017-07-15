package adamyy.github.com.kiwi.ui.common

abstract class UIEvent

data class SingleUIEvent<out T> constructor(val data: T): UIEvent()
