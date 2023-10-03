package albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.repository

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.model.ShoppingItemDto
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.toDetailData
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.repository.ShoppingListDetailRepository
import albek.petprojects.unisafetestapp.network.UniSafeDataSource
import albek.petprojects.unisafetestapp.network.retrofit.UniSafeApiHelper

class ShoppingListDetailRepositoryImpl(
    private val dataSource: UniSafeDataSource
) : ShoppingListDetailRepository {
    override suspend fun shoppingListById(id: Int): List<ShoppingItemDto> =
        dataSource.getShoppingListById(id = id).itemList.map { it.toDetailData() }

    override suspend fun removeShoppingItem(listId: Int, itemId: Int)  {
        dataSource.removeShoppingItem(
            listId = listId, itemId = itemId
        )
    }
    override suspend fun addShoppingItem(listId: Int, name: String, count: Int) {
        dataSource.addShoppingItem(id = listId, name = name, count = count)
    }
}