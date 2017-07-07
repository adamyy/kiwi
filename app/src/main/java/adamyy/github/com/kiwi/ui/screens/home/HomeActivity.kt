package adamyy.github.com.kiwi.ui.screens.home

import adamyy.github.com.kiwi.R
import adamyy.github.com.kiwi.ui.screens.base.BaseKiwiActivity
import adamyy.github.com.kiwi.databinding.HomeBinding
import android.support.design.widget.BottomNavigationView

class HomeActivity : BaseKiwiActivity<HomeBinding>() {

    companion object {
        val TAG = HomeActivity::class.simpleName
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

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

    override fun initUi() {
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val existingFragment = supportFragmentManager.findFragmentByTag(TimelineFragment.TAG)
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, existingFragment ?: TimelineFragment(), TimelineFragment.TAG)
                .commit()
    }

    override fun getLayoutRes(): Int = R.layout.activity_home
}
