package albek.petprojects.unisafetestapp.feature.insert_bottom_sheet

import albek.petprojects.unisafetestapp.R
import albek.petprojects.unisafetestapp.core.constant.FragmentResultKeys
import albek.petprojects.unisafetestapp.databinding.InsertItemsBottomSheetFragmentBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InsertBottomSheetFragment :
    BottomSheetDialogFragment(R.layout.insert_items_bottom_sheet_fragment) {
    private var _binding: InsertItemsBottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InsertItemsBottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getBundles()
    }

    private fun getBundles() {
        parentFragmentManager.setFragmentResultListener(
            FragmentResultKeys.REQUIRE_BOTTOM_SHEET_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            bundle.getBoolean(FragmentResultKeys.WITH_COUNT, false).let { isBundleForShoppingItem ->
                binding.countInputLayout.isVisible = isBundleForShoppingItem
                setListeners(isBundleForShoppingItem)
            }
        }
    }

    /** Смотрим откуда пришли bundle,
     * @param isBundleForShoppingItem - пришёл ли bundle для добавления ShoppingItem,
     * если нет, то он пришёл для ShoppingList */
    private fun setListeners(isBundleForShoppingItem: Boolean) {
        binding.run {
            closeButton.setOnClickListener { dismiss() }
            addItemButton.setOnClickListener {
                // Фрагмент для добавления ShoppingList и для добавления ShoppingItem
                // принимают разные ключи, тут мы определяем кому передать значения
                // суть в том, что одному нужно количество элементов, другому нет
                // чтобы переиспользовать этот фрагмент для ShoppingListFragment и ShoppingListDetailFragment
                // делаем такие фокусы 🤡
                val requestKey =
                    if (isBundleForShoppingItem)
                        FragmentResultKeys.REQUIRE_FROM_BOTTOM_SHEET_KEY_WITH_COUNT
                    else
                        FragmentResultKeys.REQUIRE_FROM_BOTTOM_SHEET_KEY
                if (fieldsNotEmptyCondition(isBundleForShoppingItem)) {
                    countInputLayout.error = null
                    nameInputLayout.error = null
                    parentFragmentManager.setFragmentResult(
                        requestKey, bundleOf(
                            FragmentResultKeys.NAME_KEY to binding.nameInputField.text.toString(),
                            FragmentResultKeys.COUNT_KEY to binding.countInputField.text.toString()
                        )
                    )
                    dismiss()
                } else {
                    showErrorHints()
                }
            }
        }
    }

    private fun fieldsNotEmptyCondition(isBundleForShoppingItem: Boolean) = binding.run {
        !isBundleForShoppingItem && nameInputField.text.toString()
            .isNotEmpty() || isBundleForShoppingItem && (nameInputField.text.toString()
            .isNotEmpty() && countInputField.text.toString().isNotEmpty())
    }

    private fun showErrorHints() {
        binding.run {
            countInputLayout.isErrorEnabled = true
            nameInputLayout.isErrorEnabled = true
            countInputLayout.error = getString(R.string.filed_should_be_not_empty)
            nameInputLayout.error = getString(R.string.filed_should_be_not_empty)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "bottomSheetTag"
    }
}