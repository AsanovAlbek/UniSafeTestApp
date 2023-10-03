package albek.petprojects.unisafetestapp.feature.shopping_list.domain.usecase

import albek.petprojects.unisafetestapp.feature.shopping_list.data.toDomain
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.repository.ShoppingListRepository
import javax.inject.Inject

class GetAllShoppingListsUseCase @Inject constructor(
    private val repository: ShoppingListRepository
) {
    suspend operator fun invoke() =
        repository.getAllShoppingLists().map { shopList -> shopList.toDomain() }
}