package albek.petprojects.unisafetestapp.core.base

import albek.petprojects.unisafetestapp.core.model.ErrorModel
import kotlinx.coroutines.flow.Flow
import org.orbitmvi.orbit.ContainerHost

interface ContainerHostWithErrorHandler<STATE: Any, EFFECT: Any>: ContainerHost<STATE, EFFECT> {
    val errorFlow: Flow<ErrorModel>
}