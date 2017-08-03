package adamyy.github.com.kiwi.home

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.base.BaseKiwiActivity
import adamyy.github.com.kiwi.databinding.HomeBinding
import adamyy.github.com.kiwi.home.timeline.TimelineFragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.yifan.butterfly.BActivity

@BActivity
class HomeActivity : BaseKiwiActivity<HomeContract.View, HomeContract.Presenter, HomeBinding>(), HomeContract.View {

    companion object {
        const val VISIBLE_PAGE_TAG = "VISIBLE_PAGE_TAG"
    }

    private var visiblePageTag: String = TimelineFragment.TAG

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_timeline -> {
                loadFragment(TimelineFragment.TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(visiblePageTag)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(VISIBLE_PAGE_TAG, visiblePageTag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        visiblePageTag = savedInstanceState?.getString(VISIBLE_PAGE_TAG) ?: TimelineFragment.TAG
        loadFragment(visiblePageTag)
    }

    private fun loadFragment(tag: String) {
        fun findExistingFragment(fragmentTag: String): Fragment? {
            return supportFragmentManager.findFragmentByTag(fragmentTag)
        }

        fun createFragment(fragmentTag: String): Fragment {
            return when (fragmentTag) {
                TimelineFragment.TAG -> TimelineFragment()
                else -> TODO("Implement other fragments")
            }
        }

//        if (tag == visiblePageTag) {
//            return
//        }

        val fragment = findExistingFragment(tag) ?: createFragment(tag)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    override fun getLayoutRes(): Int = R.layout.activity_home
}
