package albek.petprojects.unisafetestapp.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UniSafeClient {

    private const val BASE_URL = "https://cyberprot.ru/shopping/v2/"

    fun createClient(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}