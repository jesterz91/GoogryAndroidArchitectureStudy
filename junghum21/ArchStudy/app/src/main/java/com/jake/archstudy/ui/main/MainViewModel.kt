package com.jake.archstudy.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jake.archstudy.R
import com.jake.archstudy.base.BaseViewModel
import com.jake.archstudy.data.model.Market
import com.jake.archstudy.data.source.UpbitRepository
import com.jake.archstudy.util.ResourceProvider

class MainViewModel(
    private val repository: UpbitRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _markets = MutableLiveData<List<Market>>()
    val markets: LiveData<List<Market>> = _markets

    init {
        getMarketAll()
    }

    private fun getMarketAll() {
        repository.getMarketAll(
            { response ->
                val markets = response.asSequence()
                    .groupBy { it.market.substringBefore("-") }
                    .map { map ->
                        val title = map.key
                        val markets = map.value.joinToString { it.market }
                        Market(title, markets)
                    }
                _markets.value = markets
            },
            {
                toast.set(resourceProvider.getString(R.string.fail_network))
            }
        )
    }

}