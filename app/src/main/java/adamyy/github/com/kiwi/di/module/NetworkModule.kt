package adamyy.github.com.kiwi.di.module

import adamyy.github.com.kiwi.BuildConfig
import adamyy.github.com.kiwi.data.source.network.SigningInterceptor
import adamyy.github.com.kiwi.data.source.network.UserApi
import adamyy.github.com.kiwi.data.source.network.auth.SessionManager
import com.google.gson.Gson
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
        val consumer = OkHttpOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET)
        consumer.setTokenWithSecret("", "")
        return consumer
    }

    @Provides @Singleton
    fun provideSigningInterceptor(sessionManager: SessionManager, consumer: OkHttpOAuthConsumer): SigningInterceptor {
        return SigningInterceptor(sessionManager, consumer)
    }

    @Provides @Singleton
    fun provideOkHttpClient(interceptor: SigningInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptor)
                .build()
    }

    @Provides @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
    }

    @Provides @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
}