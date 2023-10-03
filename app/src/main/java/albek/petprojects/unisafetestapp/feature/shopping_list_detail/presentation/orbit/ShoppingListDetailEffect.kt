package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit

import androidx.annotation.StringRes

sealed class ShoppingListDetailEffect {
    data class ShowErrorMessage(@StringRes val messageId: Int): ShoppingListDetailEffect()
    object OpenBottomSheet: ShoppingListDetailEffect()
}
