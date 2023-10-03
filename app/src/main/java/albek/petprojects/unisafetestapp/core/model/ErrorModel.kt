package albek.petprojects.unisafetestapp.core.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ErrorModel(
    @StringRes val message: Int,
    @DrawableRes val icon: Int,
    val retryAction: () -> Unit = {}
)