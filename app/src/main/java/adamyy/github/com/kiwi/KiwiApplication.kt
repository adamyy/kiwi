package adamyy.github.com.kiwi

import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import adamyy.github.com.kiwi.di.DaggerAppComponent
import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class KiwiApplication: Application(), HasSupportFragmentInjector, HasActivityInjector{

    @Inject lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    @Inject lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    @Inject lateinit var sessionManager: SessionManager

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}