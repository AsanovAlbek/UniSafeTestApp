package albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.usecase

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.data.toDomain
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.repository.ShoppingListDetailRepository
import javax.inject.Inject

class ShoppingListByIdUseCase @Inject constructor(private val repository: ShoppingListDetailRepository) {
    suspend operator fun invoke(id: Int) =
        repository.shoppingListById(id = id).map { shoppingItemDto -> shoppingItemDto.toDomain() }
}