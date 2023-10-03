package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.adapter

import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.model.ShoppingItem
import androidx.recyclerview.widget.DiffUtil

class ShoppingItemDiff(
    private val oldList: List<ShoppingItem>,
    private val newList: List<ShoppingItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}