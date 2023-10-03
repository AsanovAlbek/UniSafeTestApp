package albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit

import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import androidx.annotation.StringRes

sealed class ShoppingListEffect {
    data class ErrorMessage(@StringRes val messageId: Int): ShoppingListEffect()
    data class OpenShoppingListDetails(val shoppingList: ShoppingList): ShoppingListEffect()
    object OpenAddNameBottomSheet: ShoppingListEffect()
}
