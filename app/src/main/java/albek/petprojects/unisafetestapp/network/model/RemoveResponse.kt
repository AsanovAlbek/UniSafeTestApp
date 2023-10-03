package albek.petprojects.unisafetestapp.network.model

import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.NEW_VALUE
import albek.petprojects.unisafetestapp.network.model.RemoteModelFields.SUCCESS
import com.google.gson.annotations.SerializedName

data class RemoveResponse(
    @SerializedName(value = SUCCESS)
    val isSuccess: Boolean = true,
    @SerializedName(value = NEW_VALUE)
    val isNewValue: Boolean = false
)
