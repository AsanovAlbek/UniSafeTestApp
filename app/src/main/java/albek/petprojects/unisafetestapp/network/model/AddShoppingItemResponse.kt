package albek.petprojects.unisafetestapp.network.model

import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ITEM_ID
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.SUCCESS
import com.google.gson.annotations.SerializedName

data class AddShoppingItemResponse(
    @SerializedName(value = SUCCESS)
    val isSuccess: Boolean = false,
    @SerializedName(value = ITEM_ID)
    val itemId: Int = 0
)
