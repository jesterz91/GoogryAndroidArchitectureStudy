package com.ironelder.androidarchitecture.component

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ironelder.androidarchitecture.R
import com.ironelder.androidarchitecture.common.BLOG
import com.ironelder.androidarchitecture.data.TotalModel
import com.ironelder.androidarchitecture.utils.RetrofitForNaver
import com.ironelder.androidarchitecture.view.CustomListViewAdapter
import kotlinx.android.synthetic.main.layout_search_listview.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView


class MainFragment(private val mType:String?) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)
        v.resultListView.adapter = CustomListViewAdapter(context, arrayListOf(), mType?: BLOG)
        v.resultListView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        v.resultListView.setHasFixedSize(true)
        v.resultListView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
        )
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext?:context)
        menu?.findItem(R.id.action_search)?.apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAction(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun hideKeybaord(v: View) {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    private fun searchAction(searchString: String?) {
        if(searchString.isNullOrEmpty()){
            Toast.makeText(context, getString(R.string.msg_empty_search_string), Toast.LENGTH_SHORT).show()
            return
        }
        val retrofitService = RetrofitForNaver.getSearchForNaver()
        var result = retrofitService.requestSearchForNaver(mType?: BLOG, searchString)
        result.enqueue(object : Callback<TotalModel> {
            override fun onFailure(call: Call<TotalModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TotalModel>, response: Response<TotalModel>) {
                var resultList = response.body()
                (view?.resultListView?.adapter as CustomListViewAdapter).setItemList(resultList?.items)
            }
        })
    }

}