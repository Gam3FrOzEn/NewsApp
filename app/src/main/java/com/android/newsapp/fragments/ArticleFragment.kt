package com.android.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.newsapp.adapters.NewsAdapter
import com.android.newsapp.databinding.FragmentArticleBinding
import com.android.newsapp.models.News
import com.android.newsapp.utils.PrefUtil
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

private const val ARG_PARAM1 = "param1"

class ArticleFragment : Fragment() {

    var list: MutableList<News> = ArrayList()
    private var param1: String? = null
    lateinit var binding: FragmentArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = "Articles"
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .build()

        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?sources=${param1}&apiKey=1aef82be99c14df793c0de03cdb26889")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onResponse(call: Call, response: Response) {
                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    val jsonData = response.body!!.string()
                    val obj = JSONObject(jsonData)
                    val status = obj.get("status").toString()
                    if (status == "ok") {
                        list.clear()
                        val articles: JSONArray = obj.getJSONArray("articles")

                        for (i in 0 until articles.length()) {
                            val o = articles.getJSONObject(i)

                            val author = o.getString("author")
                            val title = o.getString("title")
                            val description = o.getString("description")
                            val url = o.getString("url")
                            val urlToImage = o.getString("urlToImage")
                            val publishedAt = o.getString("publishedAt")
                            val content = o.getString("content")

                            list.add(
                                News(
                                    author,
                                    title,
                                    description,
                                    url,
                                    urlToImage,
                                    publishedAt,
                                    content
                                )
                            )

                        }
                        binding.recyclerView.adapter = NewsAdapter(list, requireActivity())
                    }
                }
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar!!.subtitle =
            "Articles opened: ${PrefUtil(requireContext()).count}"
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, id)
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}