package adamyy.github.com.kiwi.ui.base

interface BasePresenter<in V: BaseView> {
    fun start()
    fun bindView(view: V)
}