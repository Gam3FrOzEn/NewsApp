package com.android.newsapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.newsapp.R
import com.android.newsapp.fragments.DetailFragment
import com.android.newsapp.models.News
import com.bumptech.glide.Glide

class NewsAdapter(
    var mValues: MutableList<News>,
    val mCtx: Context
) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_news, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(h: MyViewHolder, position: Int) {
        val m = mValues[position]

        h.name.text = m.title
        h.desc.text =
            HtmlCompat.fromHtml(
                "<b>Author: </b>${m.author}",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

        Glide.with(mCtx).load(m.urlToImage).placeholder(R.mipmap.ic_launcher).into(h.image)

        h.itemView.setOnClickListener {
            (mCtx as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, DetailFragment.newInstance(m))
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
        val image = itemView.findViewById<ImageView>(R.id.image)
    }

    fun setFilter(newList: MutableList<News>) {
        mValues = ArrayList()
        mValues.addAll(newList)
        notifyDataSetChanged()
    }

}
