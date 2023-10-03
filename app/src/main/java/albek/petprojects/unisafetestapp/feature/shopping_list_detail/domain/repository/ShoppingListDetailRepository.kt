package albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.repository

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.model.ShoppingItemDto

interface ShoppingListDetailRepository {
    suspend fun shoppingListById(id: Int): List<ShoppingItemDto>
    suspend fun removeShoppingItem(listId: Int, itemId: Int)
    suspend fun addShoppingItem(listId: Int, name: String, count: Int)
}