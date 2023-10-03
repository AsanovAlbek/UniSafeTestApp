package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.adapter

import albek.petprojects.unisafetestapp.databinding.ShoppingItemBinding
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.model.ShoppingItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ShoppingItemsAdapter(
    private val shoppingItems: MutableList<ShoppingItem>,
    private val onItemClick: (ShoppingItem) -> Unit,
    private val onItemDelete: (itemId: Int) -> Unit
) : RecyclerView.Adapter<ShoppingItemsAdapter.ShoppingItemViewHolder>() {
    inner class ShoppingItemViewHolder(private val shoppingItemBinding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(shoppingItemBinding.root) {
        fun bind(item: ShoppingItem) {
            shoppingItemBinding.run {
                root.setOnClickListener { onItemClick(item) }
                nameTv.text = item.name
                countTv.text = item.itemCount.toString()
                removeItemButton.setOnClickListener { onItemDelete(item.id) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder =
        ShoppingItemViewHolder(
            ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = shoppingItems.count()

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        holder.bind(shoppingItems[position])
    }

    fun update(newItems: List<ShoppingItem>) {
        val diffCallback = ShoppingItemDiff(oldList = shoppingItems, newList = newItems)
        val callbackResult = DiffUtil.calculateDiff(diffCallback)
        shoppingItems.clear()
        shoppingItems.addAll(newItems)
        callbackResult.dispatchUpdatesTo(this)
    }
}