package com.android.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.newsapp.adapters.SourceAdapter
import com.android.newsapp.databinding.FragmentTopicBinding
import com.android.newsapp.models.Source
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class TopicFragment : Fragment() {

    var list: MutableList<Source> = ArrayList()
    lateinit var binding: FragmentTopicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopicBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = "Sources"
        (requireActivity() as AppCompatActivity).supportActionBar!!.subtitle = null
        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .build()

        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines/sources?apiKey=1aef82be99c14df793c0de03cdb26889")
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
                        val sources: JSONArray = obj.getJSONArray("sources")

                        for (i in 0 until sources.length()) {
                            val o = sources.getJSONObject(i)

                            val id = o.getString("id")
                            val name = o.getString("name")
                            val description = o.getString("description")
                            val url = o.getString("url")
                            val category = o.getString("category")
                            val language = o.getString("language")
                            val country = o.getString("country")

                            list.add(
                                Source(
                                    id,
                                    name,
                                    description,
                                    url,
                                    category,
                                    language,
                                    country
                                )
                            )
                        }
                        binding.recyclerView.adapter = SourceAdapter(list, requireActivity())
                    }
                }
            }
        })

        return binding.root
    }


}