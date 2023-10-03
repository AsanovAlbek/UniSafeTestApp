package albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.usecase

import albek.petprojects.unisafetestapp.core.constant.ValidateConstants
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.repository.ShoppingListDetailRepository
import javax.inject.Inject

class AddShoppingItemUseCase @Inject constructor(private val repository: ShoppingListDetailRepository) {
    suspend operator fun invoke(listId: Int, name: String, count: Int) {
        val itemCount =
            if (count > ValidateConstants.MAXIMAL_ITEMS_COUNT) ValidateConstants.MAXIMAL_ITEMS_COUNT
            else count
        repository.addShoppingItem(listId = listId, name = name, count = itemCount)
    }

}