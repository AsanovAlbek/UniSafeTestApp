package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit

import androidx.annotation.StringRes

sealed class ShoppingListDetailAction {
    data class GetShoppingItemsByListId(val listId: Int): ShoppingListDetailAction()
    data class AddShoppingItem(
        val listId: Int,
        val name: String,
        val count: Int
    ) : ShoppingListDetailAction()
    data class RemoveShoppingItem(val listId: Int, val itemId: Int): ShoppingListDetailAction()
    object RefreshItems: ShoppingListDetailAction()
    object OpenBottomSheet: ShoppingListDetailAction()
    object DoStateError: ShoppingListDetailAction()
    data class ShowErrorSnackBar(@StringRes val messageResId: Int): ShoppingListDetailAction()
}
