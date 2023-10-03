package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation

import albek.petprojects.unisafetestapp.R
import albek.petprojects.unisafetestapp.core.base.BaseViewModel
import albek.petprojects.unisafetestapp.core.model.UnsuccessfulResponseException
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ContentState
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.usecase.AddShoppingItemUseCase
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.usecase.RemoveShoppingItemUseCase
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.domain.usecase.ShoppingListByIdUseCase
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit.ShoppingListDetailAction
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit.ShoppingListDetailEffect
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit.ShoppingListDetailState
import androidx.annotation.StringRes
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class ShoppingListDetailViewModel @Inject constructor(
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val removeShoppingItemUseCase: RemoveShoppingItemUseCase,
    private val shoppingListByIdUseCase: ShoppingListByIdUseCase
) :
    BaseViewModel<ShoppingListDetailState, ShoppingListDetailAction, ShoppingListDetailEffect>() {
    private var loadingStateJob: Job? = null

    override val container = container<ShoppingListDetailState, ShoppingListDetailEffect>(
        ShoppingListDetailState()
    )

    override fun doAction(action: ShoppingListDetailAction) {
        when (action) {
            is ShoppingListDetailAction.AddShoppingItem -> addShoppingItem(
                action.listId,
                action.name,
                action.count
            )

            ShoppingListDetailAction.DoStateError -> doStateError()
            ShoppingListDetailAction.OpenBottomSheet -> openBottomSheet()
            ShoppingListDetailAction.RefreshItems -> updateItems()
            is ShoppingListDetailAction.RemoveShoppingItem -> removeShoppingItem(
                action.listId,
                action.itemId
            )

            is ShoppingListDetailAction.ShowErrorSnackBar -> showErrorMessage(action.messageResId)
            is ShoppingListDetailAction.GetShoppingItemsByListId -> updateItems(action.listId)
        }
    }

    override fun retryAction() {
        updateItems()
    }

    private fun showErrorMessage(@StringRes messageResId: Int) = intent {
        reduce { state.copy(contentState = ContentState.CONTENT) }
        postSideEffect(ShoppingListDetailEffect.ShowErrorMessage(messageId = messageResId))
    }

    private fun addShoppingItem(listId: Int, name: String, count: Int) = runOnIo {
        try {
            addShoppingItemUseCase(listId = listId, name = name, count = count)
            updateItems()
        } catch (unsuccessfulException: UnsuccessfulResponseException) {
            showErrorMessage(R.string.unsuccessful_error)
        } catch (ioException: IOException) {
            showErrorMessage(R.string.network_error)
        } catch (otherException: Exception) {
            showErrorMessage(R.string.unknown_error)
        }
    }

    private fun removeShoppingItem(listId: Int, itemId: Int) = runOnIo {
        try {
            removeShoppingItemUseCase(listId = listId, itemId = itemId)
            updateItems()
        } catch (unsuccessfulException: UnsuccessfulResponseException) {
            showErrorMessage(R.string.unsuccessful_error)
        } catch (ioException: IOException) {
            showErrorMessage(R.string.network_error)
        } catch (otherException: Exception) {
            showErrorMessage(R.string.unknown_error)
        }
    }

    private fun updateItems(listId: Int = container.stateFlow.value.listId) = runOnIo {
        intent { reduce { state.copy(listId = listId, contentState = ContentState.LOADING) } }
        loadingStateJob = shoppingListByIdUseCase(id = listId).let { shoppingItems ->
            val contentState =
                if (shoppingItems.isEmpty()) ContentState.EMPTY else ContentState.CONTENT
            intent {
                reduce {
                    state.copy(
                        listId = listId,
                        shoppingItems = shoppingItems,
                        contentState = contentState
                    )
                }
            }
        }
    }

    private fun openBottomSheet() = intent {
        postSideEffect(ShoppingListDetailEffect.OpenBottomSheet)
    }


    private fun doStateError() = intent {
        reduce { state.copy(contentState = ContentState.ERROR) }
    }


    override fun onCleared() {
        super.onCleared()
        loadingStateJob?.cancel()
        loadingStateJob = null
    }
}