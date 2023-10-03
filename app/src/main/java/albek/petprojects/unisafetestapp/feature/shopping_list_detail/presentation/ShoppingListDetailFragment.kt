package albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation

import albek.petprojects.unisafetestapp.R
import albek.petprojects.unisafetestapp.core.base.BaseFragment
import albek.petprojects.unisafetestapp.core.constant.FragmentResultKeys
import albek.petprojects.unisafetestapp.core.constant.ValidateConstants
import albek.petprojects.unisafetestapp.core.model.ErrorModel
import albek.petprojects.unisafetestapp.core.toIntOrElse
import albek.petprojects.unisafetestapp.core.toIntOrZero
import albek.petprojects.unisafetestapp.databinding.ShoppingListDetailFragmentBinding
import albek.petprojects.unisafetestapp.feature.insert_bottom_sheet.InsertBottomSheetFragment
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ContentState
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.adapter.ShoppingItemsAdapter
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit.ShoppingListDetailAction
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit.ShoppingListDetailEffect
import albek.petprojects.unisafetestapp.feature.shopping_list_detail.presentation.orbit.ShoppingListDetailState
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListDetailFragment :
    BaseFragment<ShoppingListDetailFragmentBinding, ShoppingListDetailState, ShoppingListDetailAction, ShoppingListDetailEffect, ShoppingListDetailViewModel>(
        ShoppingListDetailFragmentBinding::inflate
    ) {
    override val viewModel: ShoppingListDetailViewModel by viewModels()
    private var bottomSheet: InsertBottomSheetFragment? = null
    private lateinit var shoppingItemsAdapter: ShoppingItemsAdapter

    override fun setupListeners() {
        binding.run {
            addShoppingItemButton.setOnClickListener {
                viewModel.doAction(ShoppingListDetailAction.OpenBottomSheet)
            }
            arrowBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun setupContent() {

        setFragmentResultListener(FragmentResultKeys.REQUIRE_ID_KEY) { _, bundleFromShoppingList ->
            val listId = bundleFromShoppingList.getInt(FragmentResultKeys.ID_KEY)
            val listName = bundleFromShoppingList.getString(FragmentResultKeys.SHOPPING_LIST_NAME, "")
            binding.shoppingItemsTitle.text = getString(R.string.shopping_item_title, listName)
            viewModel.doAction(ShoppingListDetailAction.GetShoppingItemsByListId(listId = listId))
            setupAdapter(listId = listId)
            bottomSheetResultListener(listId = listId)
        }
        bottomSheet = InsertBottomSheetFragment()
    }

    private fun setupAdapter(listId: Int) {
        shoppingItemsAdapter = ShoppingItemsAdapter(
            shoppingItems = mutableListOf(),
            onItemClick = { item ->
                showSnackBar(
                    R.string.test_show_shopping_item,
                    item.name,
                    item.itemCount
                )
            },
            onItemDelete = { itemId ->
                viewModel.doAction(
                    ShoppingListDetailAction.RemoveShoppingItem(
                        listId = listId,
                        itemId = itemId
                    )
                )
            }
        )
        binding.shoppingItemsRv.adapter = shoppingItemsAdapter
    }

    private fun bottomSheetResultListener(listId: Int) {
        setFragmentResultListener(FragmentResultKeys.REQUIRE_FROM_BOTTOM_SHEET_KEY_WITH_COUNT) { requestKey, bundle ->
            bundle.getString(FragmentResultKeys.NAME_KEY)?.let { name ->
                bundle.getString(FragmentResultKeys.COUNT_KEY)?.let { count ->
                    viewModel.doAction(
                        ShoppingListDetailAction.AddShoppingItem(
                            listId = listId,
                            name = name,
                            count = count.toIntOrElse(ValidateConstants.MINIMAL_ITEMS_COUNT)
                        )
                    )
                }
            }
        }
    }

    override fun handleError(errorModel: ErrorModel) {
        viewModel.doAction(ShoppingListDetailAction.DoStateError)
        binding.errorPanel.run {
            errorIcon.setImageResource(errorModel.icon)
            errorMessage.setText(errorModel.message)
            retryButton.setOnClickListener {
                Log.d("retry", "retry")
                errorModel.retryAction()
                viewModel.doAction(ShoppingListDetailAction.RefreshItems)
            }
        }
    }

    override fun handleEffect(effect: ShoppingListDetailEffect) {
        when (effect) {
            ShoppingListDetailEffect.OpenBottomSheet -> openBottomSheet()
            is ShoppingListDetailEffect.ShowErrorMessage -> showSnackBar(effect.messageId)
        }
    }

    private fun openBottomSheet() {
        setFragmentResult(
            FragmentResultKeys.REQUIRE_BOTTOM_SHEET_KEY,
            bundleOf(FragmentResultKeys.WITH_COUNT to true)
        )
        bottomSheet?.show(parentFragmentManager, InsertBottomSheetFragment.TAG)
    }

    override fun renderState(state: ShoppingListDetailState) {
        binding.run {
            loadingProgress.isVisible = state.contentState == ContentState.LOADING
            errorPanel.root.isVisible = state.contentState == ContentState.ERROR
            shoppingItemsRv.isVisible = state.contentState == ContentState.CONTENT
            addShoppingItemButton.isVisible =
                state.contentState == ContentState.CONTENT || state.contentState == ContentState.EMPTY
            shoppingItemsTitle.isVisible =
                state.contentState == ContentState.CONTENT || state.contentState == ContentState.EMPTY
            if (state.contentState == ContentState.EMPTY) {
                errorPanel.run {
                    root.isVisible = true
                    errorIcon.isVisible = false
                    errorMessage.setText(R.string.empty_message)
                    retryButton.isVisible = false
                }
            }
        }
        shoppingItemsAdapter.update(state.shoppingItems)
    }
}