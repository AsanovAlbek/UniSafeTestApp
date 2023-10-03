package albek.petprojects.unisafetestapp.feature.shopping_list.domain.usecase

import albek.petprojects.unisafetestapp.feature.shopping_list.domain.repository.ShoppingListRepository
import javax.inject.Inject

class CreateShoppingListUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke(createdShoppingListName: String) =
        repository.createShoppingList(createdShoppingListName)
}