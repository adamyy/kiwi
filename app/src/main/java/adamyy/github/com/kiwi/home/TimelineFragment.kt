package adamyy.github.com.kiwi.home

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.data.repository.StatusRepository
import adamyy.github.com.kiwi.databinding.TimelineBinding
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import javax.inject.Inject

class TimelineFragment : BaseKiwiFragment<TimelineBinding>() {

    companion object {
        val TAG = TimelineFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_timeline

    @Inject lateinit var statusRepo: StatusRepository

    override fun onResume() {
        super.onResume()
        configureToolbar()
    }

    private fun configureToolbar() {
        val activity = getAppCompatActivity()
        activity.setSupportActionBar(binding.toolbar)
    }

}