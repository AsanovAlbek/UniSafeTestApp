package albek.petprojects.unisafetestapp.feature.shopping_list.presentation.adapter

import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import androidx.recyclerview.widget.DiffUtil

class ShoppingListDiffUtil(
    private val oldList: List<ShoppingList>,
    private val newList: List<ShoppingList>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}