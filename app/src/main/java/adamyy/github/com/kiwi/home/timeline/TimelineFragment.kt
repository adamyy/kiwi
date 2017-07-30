package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.databinding.TimelineBinding
import adamyy.github.com.kiwi.ui.base.BaseKiwiFragment
import android.os.Bundle
import android.view.View
import javax.inject.Inject

class TimelineFragment : BaseKiwiFragment<TimelineBinding>(), TimelineContract.View {

    @Inject lateinit var presenter: TimelineContract.Presenter

    companion object {
        val TAG = TimelineFragment::class.simpleName
    }

    override fun getLayoutRes(): Int = R.layout.fragment_timeline

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar()
    }

    private fun configureToolbar() {
        val activity = getAppCompatActivity()
        activity.setSupportActionBar(binding.toolbar)
    }

}