package com.weather.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.text.format.DateUtils
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.weather.R
import com.weather.databinding.FragmentWeatherBinding
import com.weather.db.WeatherDao
import com.weather.model.WeatherModel
import com.weather.util.AndroidUtilities
import com.weather.util.Loader
import com.weather.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var weatherDao: WeatherDao

    private val viewModel by viewModels<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherDao.loadAll().observe(viewLifecycleOwner, {
            if(it.isEmpty()) {
                return@observe
            }

            for(k in it) {
                if(!DateUtils.isToday(k.dt.toLong() * 1000)) {
                    weatherDao.delete(k)
                }
            }
        })

        viewModel.repo.observe(viewLifecycleOwner, {

            when (it.status) {

                Status.LOADING -> {
                    Loader.showLoading(requireContext())
                }

                Status.SUCCESS -> {
                    Loader.stopLoading()
                    binding.cardView.visibility = View.VISIBLE
                    binding.captionTextView.visibility = View.INVISIBLE
                    updateUI(it.data)
                }

                Status.ERROR -> {
                    Loader.stopLoading()
                    binding.cardView.visibility = View.GONE
                    binding.captionTextView.text = it.message
                    binding.captionTextView.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_menu, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.let {
            it.queryHint = "Enter city name"
            it.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    updateDetails(text)
                    searchItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun updateDetails(name: String?) {
        if (TextUtils.isEmpty(name)) {
            return
        }
        viewModel.setId(
            getString(R.string.weather_api_key),
            name?.capitalize(Locale.getDefault()).toString()
        )
    }

    private fun updateUI(data: WeatherModel?) {
        data?.let {
            binding.cityNameTextView.text = it.name
            binding.dateTextView.text = AndroidUtilities.toDate(it.dt.toLong())
            binding.sunriseTextView.text = AndroidUtilities.toTime(it.sys.sunrise.toLong())
            binding.sunsetTextView.text = AndroidUtilities.toTime(it.sys.sunset.toLong())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}