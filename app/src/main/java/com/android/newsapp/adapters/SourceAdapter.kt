package com.android.newsapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.newsapp.R
import com.android.newsapp.fragments.ArticleFragment
import com.android.newsapp.models.Source

class SourceAdapter(
    var mValues: MutableList<Source>,
    val mCtx: Context
) : RecyclerView.Adapter<SourceAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_source, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(h: MyViewHolder, position: Int) {
        val m = mValues[position]

        h.name.text = m.name
        h.desc.text =
            HtmlCompat.fromHtml("<b>Category: </b>${m.category}", HtmlCompat.FROM_HTML_MODE_LEGACY)

        h.itemView.setOnClickListener {
            (mCtx as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ArticleFragment.newInstance(m.id!!))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val desc = itemView.findViewById<TextView>(R.id.desc)
    }

    fun setFilter(newList: MutableList<Source>) {
        mValues = ArrayList()
        mValues.addAll(newList)
        notifyDataSetChanged()
    }

}
