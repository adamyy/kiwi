package adamyy.github.com.kiwi.home.timeline

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(
        TimelineModule::class
))
interface TimelineSubComponent : AndroidInjector<TimelineFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TimelineFragment>()

}