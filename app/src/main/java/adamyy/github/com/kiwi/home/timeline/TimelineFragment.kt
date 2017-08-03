package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.entity.Tweet
import adamyy.github.com.kiwi.databinding.TimelineBinding
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_timeline.*
import javax.inject.Inject

class TimelineFragment : BaseKiwiFragment<TimelineBinding>(), TimelineContract.View {

    @Inject lateinit var presenter: TimelineContract.Presenter

    private val timelineAdapter = TimelineAdapter()

    companion object {
        const val TAG = "TimelineFragment"
    }

    override fun getLayoutRes(): Int = R.layout.fragment_timeline

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        configureToolbar()
        timeline_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        timeline_list.adapter = timelineAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private fun configureToolbar() {
        val activity = getAppCompatActivity()
        activity.setSupportActionBar(binding.toolbar)
    }

    override fun showLoading() {

    }

    override fun showTimeline(timeline: List<Tweet>) {
        timelineAdapter.appendTweets(timeline)
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}