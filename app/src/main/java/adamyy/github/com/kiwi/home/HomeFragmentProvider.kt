package adamyy.github.com.kiwi.home

import adamyy.github.com.kiwi.home.timeline.TimelineFragment
import adamyy.github.com.kiwi.home.timeline.TimelineSubComponent
import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentProvider {

    @Binds @IntoMap @FragmentKey(TimelineFragment::class)
    abstract fun provideTimelineFragmentInjectorFactory(builder: TimelineSubComponent.Builder): AndroidInjector.Factory<out Fragment>

}