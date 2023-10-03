package albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit

import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import androidx.annotation.StringRes

sealed class ShoppingListAction {
    data class DeleteShoppingList(val id: Int): ShoppingListAction()
    data class AddShoppingList(val name: String): ShoppingListAction()
    object OpenBottomSheet: ShoppingListAction()
    data class OpenDetails(val shoppingList: ShoppingList): ShoppingListAction()
    object RefreshList: ShoppingListAction()
    object DoStateError: ShoppingListAction()
    data class ShowErrorSnackBar(@StringRes val messageId: Int = 0): ShoppingListAction()
}
