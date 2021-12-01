package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.util.Coroutines
import com.example.simpleweatherapp.viewmodel.WeatherViewModel
import com.example.simpleweatherapp.viewmodel.WeatherViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HistoricalFragment : Fragment(), KodeinAware, OnClick {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel
    private var recyclerview: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.historical_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        recyclerview = view?.findViewById(R.id.recyclerview)
        bindUI()
    }


    private fun bindUI() = Coroutines.main {
        manageWeatherList()

    }

    private fun manageWeatherList() {

        val list = viewModel.getWeatherListData()?.filter {
            it.cityName == viewModel.getSelectedItemData()?.cityName
        }
        list?.toWeatherViewItem()?.let { initRecyclerView(it) }
    }

    private fun initRecyclerView(weatherList: List<HistoricalWeatherViewItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(weatherList)
        }
        recyclerview?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    private fun List<CityWeatherItem>.toWeatherViewItem(): List<HistoricalWeatherViewItem> {
        return this.map {
            HistoricalWeatherViewItem(it, this@HistoricalFragment)
        }
    }

    override fun onItemClick(item: CityWeatherItem) {
        viewModel.setSelectedItemData(item)
        navToWeatherInfoFragment()
    }

    private fun navToWeatherInfoFragment() {
        val navController =
            activity?.findNavController(R.id.nav_host_fragment)
        navController?.navigate(R.id.weatherInfoFragment)
    }

}
