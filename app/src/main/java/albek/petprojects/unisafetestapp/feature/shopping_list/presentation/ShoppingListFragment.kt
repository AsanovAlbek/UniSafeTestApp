package albek.petprojects.unisafetestapp.feature.shopping_list.presentation

import albek.petprojects.unisafetestapp.R
import albek.petprojects.unisafetestapp.core.base.BaseFragment
import albek.petprojects.unisafetestapp.core.constant.FragmentResultKeys
import albek.petprojects.unisafetestapp.core.model.ErrorModel
import albek.petprojects.unisafetestapp.databinding.ShoppingListFragmentBinding
import albek.petprojects.unisafetestapp.feature.insert_bottom_sheet.InsertBottomSheetFragment
import albek.petprojects.unisafetestapp.feature.shopping_list.domain.model.ShoppingList
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.adapter.ShoppingListAdapter
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ContentState
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ShoppingListAction
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ShoppingListEffect
import albek.petprojects.unisafetestapp.feature.shopping_list.presentation.orbit.ShoppingListState
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.viewmodel.observe

@AndroidEntryPoint
class ShoppingListFragment :
    BaseFragment<ShoppingListFragmentBinding, ShoppingListState, ShoppingListAction, ShoppingListEffect, ShoppingListViewModel>(
        ShoppingListFragmentBinding::inflate
    ) {

    override val viewModel: ShoppingListViewModel by viewModels()
    private lateinit var adapter: ShoppingListAdapter
    private var bottomSheet: InsertBottomSheetFragment? = null

    override fun setupListeners() {
        binding.run {
            addShoppingListButton.setOnClickListener {
                viewModel.doAction(ShoppingListAction.OpenBottomSheet)
            }
        }
    }

    override fun setupContent() {
        bottomSheet = InsertBottomSheetFragment()
        adapter = ShoppingListAdapter(
            shoppingListItems = mutableListOf(),
            onShopItemClick = { shoppingList ->
                viewModel.doAction(
                    ShoppingListAction.OpenDetails(
                        shoppingList
                    )
                )
            },
            onShopItemRemove = { id -> viewModel.doAction(ShoppingListAction.DeleteShoppingList(id)) }
        )
        binding.shoppingListRv.adapter = adapter

        setFragmentResultListener(FragmentResultKeys.REQUIRE_FROM_BOTTOM_SHEET_KEY) { _, bundle ->
            bundle.getString(FragmentResultKeys.NAME_KEY)?.let { name ->
                viewModel.doAction(ShoppingListAction.AddShoppingList(name = name))
            }
        }
    }

    override fun handleError(errorModel: ErrorModel) {
        viewModel.doAction(ShoppingListAction.DoStateError)
        binding.errorPanel.run {
            errorIcon.setImageResource(errorModel.icon)
            errorMessage.setText(errorModel.message)
            retryButton.setOnClickListener { errorModel.retryAction() }
        }
    }

    override fun renderState(state: ShoppingListState) {
        binding.run {
            loadingProgress.isVisible = state.contentState == ContentState.LOADING
            errorPanel.root.isVisible = state.contentState == ContentState.ERROR
            shoppingListRv.isVisible = state.contentState == ContentState.CONTENT
            addShoppingListButton.isVisible =
                state.contentState == ContentState.CONTENT || state.contentState == ContentState.EMPTY
            shoppingListsTitle.isVisible =
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
        adapter.update(state.shoppingLists)
    }

    override fun handleEffect(effect: ShoppingListEffect) {
        Log.d("orbit", "effect handle")
        when (effect) {
            is ShoppingListEffect.ErrorMessage -> showSnackBar(effect.messageId)
            is ShoppingListEffect.OpenShoppingListDetails -> openShoppingListDetails(effect.shoppingList)
            ShoppingListEffect.OpenAddNameBottomSheet -> openBottomSheetForAddShoppingList()
        }
    }

    private fun openShoppingListDetails(shoppingList: ShoppingList) {
        setFragmentResult(
            FragmentResultKeys.REQUIRE_ID_KEY,
            bundleOf(
                FragmentResultKeys.ID_KEY to shoppingList.id,
                FragmentResultKeys.SHOPPING_LIST_NAME to shoppingList.name
            )
        )
        findNavController().navigate(R.id.action_shoppingListFragment_to_shoppingListDetailFragment)
    }

    private fun openBottomSheetForAddShoppingList() {
        setFragmentResult(
            FragmentResultKeys.REQUIRE_BOTTOM_SHEET_KEY,
            bundleOf(
                FragmentResultKeys.WITH_COUNT to false
            )
        )
        bottomSheet?.show(parentFragmentManager, InsertBottomSheetFragment.TAG)
    }
}