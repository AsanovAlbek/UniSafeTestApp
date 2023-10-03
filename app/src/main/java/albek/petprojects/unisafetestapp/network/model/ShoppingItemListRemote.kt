package albek.petprojects.unisafetestapp.network.model

import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ITEM_LIST
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.SUCCESS
import com.google.gson.annotations.SerializedName

data class ShoppingItemListRemote(
    @SerializedName(value = ITEM_LIST)
    val itemList: List<ShoppingItemRemote> = emptyList(),
    @SerializedName(value = SUCCESS)
    val isSuccess: Boolean = false
)