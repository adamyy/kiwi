package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.data.entity.Tweet
import adamyy.github.com.kiwi.ui.base.BasePresenter
import adamyy.github.com.kiwi.ui.base.BaseView

interface TimelineContract {

    interface Presenter : BasePresenter<View> {

    }

    interface View : BaseView {
        fun showLoading()
        fun showTimeline(timeline: List<Tweet>)
        fun showError(error: String)
    }
}


