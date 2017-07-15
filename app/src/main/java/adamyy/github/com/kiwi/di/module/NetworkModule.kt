package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.data.source.network.AuthApi
import adamyy.github.com.kiwi.data.source.network.TwitterApiConstant
import adamyy.github.com.kiwi.data.source.network.TwitterOAuthInterceptor
import adamyy.github.com.kiwi.data.source.network.UserApi
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

    @Provides @Singleton
    fun provideOAuthConsumer(): OkHttpOAuthConsumer {
        val consumer = OkHttpOAuthConsumer(TwitterApiConstant.CONSUMER_KEY, TwitterApiConstant.CONSUMER_SECRET)
        consumer.setTokenWithSecret("", "")
        return consumer
    }

    @Provides @Singleton
    fun provideOAuthInterceptor(consumer: OkHttpOAuthConsumer): TwitterOAuthInterceptor {
        return TwitterOAuthInterceptor(consumer)
    }

    @Provides @Singleton
    fun provideOkHttpClient(interceptor: TwitterOAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptor)
                .build()
    }

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(TwitterApiConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}