package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.data.repository.StatusRepository
import javax.inject.Inject

class TimelinePresenter @Inject constructor(
        private val statusRepo: StatusRepository
): TimelineContract.Presenter {



    override fun bindView(view: TimelineContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {

    }
}