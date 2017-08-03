package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.data.repository.StatusRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TimelinePresenter @Inject constructor(
        private val statusRepo: StatusRepository
): TimelineContract.Presenter {

    private lateinit var view: TimelineContract.View

    override fun bindView(view: TimelineContract.View) {
        this.view = view
    }

    override fun start() {
        loadTimeline()
    }

    private fun loadTimeline() {
        statusRepo.getHomeTimeline()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when {
                        it.inFlight -> view.showLoading()
                        it.isSuccess -> view.showTimeline(it.data!!)
                        else -> view.showError(it.error!!)
                    }
                }
    }
}