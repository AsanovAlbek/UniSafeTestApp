package albek.petprojects.unisafetestapp.feature.shopping_list.domain.repository

import albek.petprojects.unisafetestapp.feature.shopping_list.data.model.ShoppingListDto

interface ShoppingListRepository {
    suspend fun createShoppingList(name: String)
    suspend fun removeShoppingList(removedListId: Int)
    suspend fun getAllShoppingLists(): List<ShoppingListDto>
}