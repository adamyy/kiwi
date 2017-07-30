package adamyy.github.com.kiwi.home.timeline

import adamyy.github.com.kiwi.data.repository.StatusRepository
import dagger.Module
import dagger.Provides

@Module
class TimelineModule {

    @Provides
    fun provideTimelinePresenter(statusRepository: StatusRepository): TimelineContract.Presenter {
        return TimelinePresenter(statusRepository)
    }

}
