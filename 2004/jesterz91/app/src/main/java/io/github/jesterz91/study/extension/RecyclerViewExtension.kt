package io.github.jesterz91.study.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.jesterz91.study.common.BaseAdapter

@BindingAdapter(value = ["fixedSize", "itemDecoration"], requireAll = false)
fun RecyclerView.init(hasFixedSize: Boolean?, itemDecoration: RecyclerView.ItemDecoration?) {
    hasFixedSize?.run(::setHasFixedSize)
    itemDecoration?.run(::addItemDecoration)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(items: List<Any>?) {
    (adapter as? BaseAdapter<Any, *>)?.run {
        items?.run(::submitList)
    }
}