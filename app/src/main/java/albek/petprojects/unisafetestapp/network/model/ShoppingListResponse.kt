package albek.petprojects.unisafetestapp.network.model

import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.SHOP_LIST
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.SUCCESS
import com.google.gson.annotations.SerializedName

data class ShoppingListResponse(
    @SerializedName(value = SHOP_LIST)
    val shopList: List<ShoppingListRemote> = emptyList(),
    @SerializedName(value = SUCCESS)
    val isSuccess: Boolean = false
)
