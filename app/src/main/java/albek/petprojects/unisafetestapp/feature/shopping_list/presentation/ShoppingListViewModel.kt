package albek.petprojects.unisafetestapp.feature.shopping_list.presentation

import albek.petprojects.unisafetestapp.R
import albek.petprojects.unisafetestapp.core.base.BaseViewModel
import albek.petprojects.unisafetestapp.core.model.ErrorModel
import albek.petprojects.unisafetestapp.core.model.UnsuccessfulResponseException
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.usecase.CreateShoppingListUseCase
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.usecase.GetAllShoppingListsUseCase
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.usecase.RemoveShoppingListUseCase
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ContentState
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ShoppingListAction
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ShoppingListEffect
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ShoppingListState
import android.util.Log
import androidx.annotation.StringRes
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val createShoppingListUseCase: CreateShoppingListUseCase,
    private val removeShoppingListUseCase: RemoveShoppingListUseCase,
    private val getAllShoppingListsUseCase: GetAllShoppingListsUseCase
) : BaseViewModel<ShoppingListState, ShoppingListAction, ShoppingListEffect>() {
    private var loadingStateJob: Job? = null


    override val container = container<ShoppingListState, ShoppingListEffect>(ShoppingListState()) {
        loadingStateJob = updateShoppingLists()
    }

    override fun doAction(action: ShoppingListAction) {
        Log.d("orbit", "action = ${action.javaClass.simpleName}")
        when (action) {
            is ShoppingListAction.AddShoppingList -> addShoppingList(action.name)
            is ShoppingListAction.DeleteShoppingList -> removeShoppingList(action.id)
            is ShoppingListAction.OpenDetails -> openShoppingListDetails(action.shoppingList)
            ShoppingListAction.RefreshList -> updateShoppingLists()
            ShoppingListAction.OpenBottomSheet -> openBottomSheet()
            ShoppingListAction.DoStateError -> doStateError()
            is ShoppingListAction.ShowErrorSnackBar -> showErrorSnackBar(action.messageId)
        }
    }

    override fun retryAction() {
        updateShoppingLists()
    }

    private fun showErrorSnackBar(@StringRes messageId: Int) = intent {
        // Ловим ошибку, но показываем контент, а ошибку показываем в снек баре
        reduce { state.copy(contentState = ContentState.CONTENT) }
        postSideEffect(ShoppingListEffect.ErrorMessage(messageId = messageId))
    }

    private fun doStateError() = intent {
        reduce { state.copy(contentState = ContentState.ERROR) }
    }

    private fun openBottomSheet() =
        intent { postSideEffect(ShoppingListEffect.OpenAddNameBottomSheet) }

    private fun openShoppingListDetails(shoppingList: ShoppingList) =
        intent { postSideEffect(ShoppingListEffect.OpenShoppingListDetails(shoppingList = shoppingList)) }

    private fun updateShoppingLists() = runOnIo {
            intent { reduce { state.copy(contentState = ContentState.LOADING) } }
            getAllShoppingListsUseCase().let { shopList ->
                val contentState =
                    if (shopList.isEmpty()) ContentState.EMPTY else ContentState.CONTENT
                intent {
                    reduce {
                        state.copy(
                            shoppingLists = shopList,
                            contentState = contentState
                        )
                    }
                }
            }
    }

    private fun removeShoppingList(id: Int) = runOnIoWithBlocking {
        try {
            removeShoppingListUseCase(removedShoppingListId = id)
            updateShoppingLists()
        } catch (unsuccessfulException: UnsuccessfulResponseException) {
            showErrorSnackBar(R.string.unsuccessful_error)
        } catch (ioException: IOException) {
            showErrorSnackBar(R.string.network_error)
        } catch (exception: Exception) {
            showErrorSnackBar(R.string.unknown_error)
        }
    }


    private fun addShoppingList(name: String) = runOnIoWithBlocking {
        try {
            createShoppingListUseCase(createdShoppingListName = name)
            updateShoppingLists()
        } catch (unsuccessfulException: UnsuccessfulResponseException) {
            showErrorSnackBar(R.string.unsuccessful_error)
        } catch (ioException: IOException) {
            showErrorSnackBar(R.string.network_error)
        } catch (exception: Exception) {
            showErrorSnackBar(R.string.unknown_error)
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadingStateJob?.cancel()
        loadingStateJob = null
    }
}