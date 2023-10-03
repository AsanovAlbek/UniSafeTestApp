package albek.petprojects.unisafetestapp.feature.shopping_list.data.repository

import albek.petprojects.unisafetestapp.feature.shopping_list.data.model.ShoppingListDto
import albek.petprojects.unisafetestapp.feature.shopping_list.data.toData
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.repository.ShoppingListRepository
import albek.petprojects.unisafetestapp.network.UniSafeDataSource
import android.util.Log

class ShoppingListRepositoryImpl(
    private val uniSafeDataSource: UniSafeDataSource
) : ShoppingListRepository {
    override suspend fun createShoppingList(name: String) =
        uniSafeDataSource.createShoppingList(name = name)

    override suspend fun removeShoppingList(removedListId: Int) =
        uniSafeDataSource.removeShoppingList(id = removedListId)

    override suspend fun getAllShoppingLists(): List<ShoppingListDto> =
        uniSafeDataSource.getAllShoppingLists().shopList.map { shopList -> shopList.toData() }
}