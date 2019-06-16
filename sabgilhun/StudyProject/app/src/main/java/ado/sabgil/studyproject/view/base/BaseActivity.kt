package ado.sabgil.studyproject.view.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity() {

    protected lateinit var binding: B
        private set

    protected var progressBar: View? = null

    protected val viewModelContainer = mutableListOf<BaseViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        for (viewModel in viewModelContainer) {
            viewModel.onDestroy()
        }
        super.onDestroy()
    }

    protected fun showToastMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    protected fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    protected fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }

    protected fun bind(block: B.() -> Unit) {
        binding.block()
    }

    protected fun <T : BaseViewModel> addingToContainer(block: () -> T): T {
        return block().apply {
            viewModelContainer.add(this)
        }
    }
}