package albek.petprojects.unisafetestapp.feature.shopping_list.presentation.adapter

import albek.petprojects.unisafetestapp.databinding.ShoppingListItemBinding
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(
    private val shoppingListItems: MutableList<ShoppingList>,
    private val onShopItemClick: (shoppingList: ShoppingList) -> Unit,
    private val onShopItemRemove: (id: Int) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {
    inner class ShoppingListViewHolder(private val shoppingListItemBinding: ShoppingListItemBinding) :
        RecyclerView.ViewHolder(shoppingListItemBinding.root) {
        fun bind(shoppingList: ShoppingList) {
            shoppingListItemBinding.run {
                nameTv.text = shoppingList.name
                createdTimeTv.text = shoppingList.created
                // Обработка нажатия на элемент списка, передаю id для дальнейшей навигации по нему
                root.setOnClickListener { onShopItemClick(shoppingList) }
                removeItemButton.setOnClickListener { onShopItemRemove(shoppingList.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder =
        ShoppingListViewHolder(
            ShoppingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = shoppingListItems.size

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(shoppingList = shoppingListItems[position])
    }

    fun update(newShoppingListItems: List<ShoppingList>) {
        val diffCallback = ShoppingListDiffUtil(oldList = shoppingListItems, newList = newShoppingListItems)
        val callbackResult = DiffUtil.calculateDiff(diffCallback)
        shoppingListItems.clear()
        shoppingListItems.addAll(newShoppingListItems)
        callbackResult.dispatchUpdatesTo(this)
    }
}