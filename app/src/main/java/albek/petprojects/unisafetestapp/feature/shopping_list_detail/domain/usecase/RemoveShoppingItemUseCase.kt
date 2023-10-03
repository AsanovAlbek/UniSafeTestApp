package albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.usecase

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.repository.ShoppingListDetailRepository
import javax.inject.Inject

class RemoveShoppingItemUseCase @Inject constructor(private val repository: ShoppingListDetailRepository) {
    suspend operator fun invoke(listId: Int, itemId: Int) =
        repository.removeShoppingItem(listId = listId, itemId = itemId)
}