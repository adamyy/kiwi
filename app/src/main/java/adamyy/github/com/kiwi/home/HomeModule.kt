package adamyy.github.com.kiwi.home

import adamyy.github.com.kiwi.home.timeline.TimelineSubComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = arrayOf(
        TimelineSubComponent::class
))
class HomeModule {

    @Provides
    fun provideHomePresenter(): HomeContract.Presenter {
        return HomePresenter()
    }
}
