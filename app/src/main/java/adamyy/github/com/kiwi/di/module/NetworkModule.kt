package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.data.source.network.TwitterApiConstant
import adamyy.github.com.kiwi.data.source.network.TwitterOAuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOAuthConsumer(): OkHttpOAuthConsumer {
        return OkHttpOAuthConsumer(TwitterApiConstant.CONSUMER_KEY, TwitterApiConstant.CONSUMER_SECRET)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(consumer: OkHttpOAuthConsumer): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(TwitterOAuthInterceptor(consumer))
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(TwitterApiConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

}