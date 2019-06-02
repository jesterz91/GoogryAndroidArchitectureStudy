package ado.sabgil.studyproject.view.home

import ado.sabgil.studyproject.data.CoinRepository
import ado.sabgil.studyproject.view.base.BaseViewModel
import io.reactivex.rxkotlin.addTo
import kotlin.properties.Delegates

class HomeViewModel(private val coinRepository: CoinRepository) : BaseViewModel() {

    var marketListListeners: ((List<String>) -> Unit)? = null

    private var marketList: List<String> by Delegates.observable(emptyList()) { _, _, newValue ->
        marketListListeners?.invoke(newValue)
    }

    fun loadMarketList() {
        showProgressBar()

        coinRepository.loadMarketList(
            { response ->
                hideProgressBar()
                marketList = response
            }, { error ->
                hideProgressBar()
                showToast(error)
            }
        ).addTo(disposables)
    }
}