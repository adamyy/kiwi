package adamyy.github.com.kiwi.di.features

import adamyy.github.com.kiwi.ui.features.home.TimelineFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface TimelineSubComponent: AndroidInjector<TimelineFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TimelineFragment>()

}