package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit

import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ContentState
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.model.ShoppingItem

data class ShoppingListDetailState(
    val listId: Int = 0,
    val shoppingItems: List<ShoppingItem> = emptyList(),
    val contentState: ContentState = ContentState.LOADING
)
