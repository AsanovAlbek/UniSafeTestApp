package albek.petprojects.unisafetestapp.network.model

import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.LIST_ID
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.SUCCESS
import com.google.gson.annotations.SerializedName

data class AddShoppingListResponse(
    @SerializedName(value = SUCCESS)
    val isSuccess: Boolean = false,
    @SerializedName(value = LIST_ID)
    val listId: Int = 0
)
