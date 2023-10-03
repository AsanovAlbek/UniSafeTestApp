package albek.petprojects.unisafetestapp.core.base

import albek.petprojects.unisafetestapp.R
import albek.petprojects.unisafetestapp.core.model.ErrorModel
import albek.petprojects.unisafetestapp.core.model.UnsuccessfulResponseException
import albek.petprojects.unisafetestapp.core.model.isNetworkException
import albek.petprojects.unisafetestapp.core.model.isUnsuccessfulResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.Executors
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class BaseViewModel<STATE : Any, ACTION : Any, EFFECT : Any> :
    ContainerHostWithErrorHandler<STATE, EFFECT>, ViewModel() {
    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    override val errorFlow: Flow<ErrorModel> get() = _errorFlow
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("baseVM", throwable.stackTrace.joinToString(separator = "\n"))
        when (throwable) {
            is IOException -> throwError(
                ErrorModel(
                    message = R.string.network_error,
                    icon = R.drawable.connection_lost_icon,
                    retryAction = { retryAction() }
                )
            )

            is UnsuccessfulResponseException -> throwError(
                ErrorModel(
                    message = R.string.unsuccessful_error,
                    icon = R.drawable.unsuccessful_icon,
                    retryAction = { retryAction() }
                )
            )

            else -> throwError(
                ErrorModel(
                    message = R.string.unknown_error,
                    icon = R.drawable.unsuccessful_icon,
                    retryAction = { retryAction() }
                )
            )
        }
    }
    private val uiScope =
        CoroutineScope(SupervisorJob() + viewModelScope.coroutineContext + exceptionHandler)
    private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)
    private val defaultScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + exceptionHandler)

    fun runOnUi(action: suspend () -> Unit) = uiScope.launch { action() }
    fun runOnIo(action: suspend () -> Unit) = ioScope.launch { action() }
    fun runOnDefault(action: suspend () -> Unit) = defaultScope.launch { action() }
    fun runOnIoWithBlocking(action: suspend () -> Unit) =
        runBlocking(SupervisorJob() + ioScope.coroutineContext + exceptionHandler) { action() }

    abstract fun doAction(action: ACTION)
    abstract fun retryAction()
    fun throwError(errorModel: ErrorModel) {
        viewModelScope.launch {
            _errorFlow.emit(errorModel)
        }
    }
}