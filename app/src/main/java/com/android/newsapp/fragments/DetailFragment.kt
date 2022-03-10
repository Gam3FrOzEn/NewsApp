package com.android.newsapp.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.android.newsapp.R
import com.android.newsapp.databinding.FragmentDetailBinding
import com.android.newsapp.models.News
import com.android.newsapp.utils.PrefUtil
import com.bumptech.glide.Glide

private const val ARG_PARAM1 = "param1"

class DetailFragment : Fragment() {

    private var news: News? = null
    lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            news = it.getSerializable(ARG_PARAM1) as News
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = news!!.title
        (requireActivity() as AppCompatActivity).supportActionBar!!.subtitle = null

        Glide.with(requireActivity()).load(news!!.urlToImage).into(binding.image)

        val old = PrefUtil(requireContext()).count

        val updateCount = old + 1
        PrefUtil(requireContext()).count = updateCount

        binding.title.text = news!!.title
        binding.desc.text = news!!.description
        binding.author.text =
            HtmlCompat.fromHtml("<b>Author: </b>${news!!.author}", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.publish.text = HtmlCompat.fromHtml(
            "<b>Publish at: </b>${news!!.publishedAt}",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        binding.gotoBrowser.setOnClickListener {
            startCustomTab(news!!.url!!)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(news: News) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, news)
                }
            }
    }

    fun startCustomTab(url: String) {
        val customIntent = CustomTabsIntent.Builder()
        customIntent.setToolbarColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.white
            )
        )
        customIntent.addDefaultShareMenuItem()
        customIntent.setStartAnimations(
            requireActivity(),
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        customIntent.setExitAnimations(
            requireActivity(), android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        val i = customIntent.build()
        i.intent.setPackage("com.android.chrome")
        i.launchUrl(requireActivity(), Uri.parse(url))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}