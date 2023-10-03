package albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit

import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList

data class ShoppingListState(
    val shoppingLists: List<ShoppingList> = emptyList(),
    val contentState: ContentState = ContentState.LOADING
)

enum class ContentState {
    LOADING, ERROR, CONTENT, EMPTY
}
