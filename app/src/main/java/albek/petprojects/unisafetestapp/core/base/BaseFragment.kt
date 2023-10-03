package albek.petprojects.unisafetestapp.core.base

import albek.petprojects.unisafetestapp.core.model.ErrorModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.observe

fun <STATE: Any, EFFECT: Any> ContainerHostWithErrorHandler<STATE, EFFECT>.observeWithErrorHandling(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    state: (suspend (STATE) -> Unit)? = null,
    sideEffect: (suspend (EFFECT) -> Unit)? = null,
    error: (suspend (ErrorModel) -> Unit)? = null
) {
    observe(
        lifecycleOwner = lifecycleOwner,
        state = state,
        sideEffect = sideEffect,
        lifecycleState = Lifecycle.State.STARTED
    )
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            error?.let { errorFlow.collect { error(it) } }
        }
    }
}

abstract class BaseFragment<BINDING : ViewBinding, STATE : Any, ACTION : Any, EFFECT : Any, VM : BaseViewModel<STATE, ACTION, EFFECT>>(
    private val bindingInflater: (LayoutInflater) -> BINDING
) : Fragment() {

    private var _binding: BINDING? = null
    val binding get() = _binding!!

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupContent()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel.observeWithErrorHandling(
            lifecycleState = Lifecycle.State.STARTED,
            lifecycleOwner = viewLifecycleOwner,
            state = ::renderState,
            sideEffect = ::handleEffect,
            error = ::handleError
        )
    }

    abstract fun renderState(state: STATE)
    abstract fun handleEffect(effect: EFFECT)
    abstract fun handleError(errorModel: ErrorModel)
    abstract fun setupListeners()
    abstract fun setupContent()

    protected fun showSnackBar(@StringRes message: Int) =
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()

    protected fun showSnackBar(@StringRes message: Int, vararg args: Any) {
        val snackBarMessage = getString(message, args)
        Snackbar.make(requireView(), snackBarMessage, Snackbar.LENGTH_SHORT).show()
    }

}