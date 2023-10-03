package albek.petprojects.unisafetestapp.network.model

import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.CREATED
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ID
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.ITEM_COUNT
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.NAME
import com.google.gson.annotations.SerializedName

data class ShoppingItemRemote(
    @SerializedName(value = ID)
    val id: Int = 0,
    @SerializedName(value = NAME)
    val name: String = "",
    @SerializedName(value = CREATED)
    val count: Int = 0
)