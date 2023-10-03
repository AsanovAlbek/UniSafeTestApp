package albek.petprojects.unisafetestapp.network.di

import albek.petprojects.unisafetestapp.network.UniSafeDataSource
import albek.petprojects.unisafetestapp.network.retrofit.UniSafeApiHelper
import albek.petprojects.unisafetestapp.network.retrofit.UniSafeClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideUniSafeClient(): Retrofit = UniSafeClient.createClient()

    @Provides
    @Singleton
    fun provideUniSafeApiHelper(retrofit: Retrofit): UniSafeApiHelper =
        retrofit.create(UniSafeApiHelper::class.java)

    @Provides
    @Singleton
    fun provideUniSafeDataSource(apiHelper: UniSafeApiHelper): UniSafeDataSource =
        UniSafeDataSource(apiHelper = apiHelper)
}