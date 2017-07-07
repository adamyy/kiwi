package adamyy.github.com.kiwi

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class KiwiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
    }
}