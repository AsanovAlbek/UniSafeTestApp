package albek.petprojects.unisafetestapp.feature.shopping_list.di

import albek.petprojects.unisafetestapp.feature.shopping_list.data.repository.ShoppingListRepositoryImpl
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.repository.ShoppingListRepository
import albek.petprojects.unisafetestapp.network.UniSafeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ShoppingListModule {
    @Provides
    fun provideShoppingListRepository(dataSource: UniSafeDataSource): ShoppingListRepository =
        ShoppingListRepositoryImpl(dataSource)
}