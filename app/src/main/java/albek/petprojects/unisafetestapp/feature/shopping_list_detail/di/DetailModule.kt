package albek.petprojects.unisafetestapp.feature.shopping_list_detail.di

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.repository.ShoppingListDetailRepositoryImpl
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.repository.ShoppingListDetailRepository
import albek.petprojects.unisafetestapp.network.UniSafeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DetailModule {
    @Provides
    fun provideDetailRepository(dataSource: UniSafeDataSource): ShoppingListDetailRepository =
        ShoppingListDetailRepositoryImpl(dataSource)
}