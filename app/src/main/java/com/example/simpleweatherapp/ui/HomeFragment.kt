package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.util.Coroutines
import com.example.simpleweatherapp.util.hide
import com.example.simpleweatherapp.util.show
import com.example.simpleweatherapp.viewmodel.WeatherViewModel
import com.example.simpleweatherapp.viewmodel.WeatherViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel
    private var progressBar: ProgressBar? = null
    private var recyclerview: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        progressBar = view?.findViewById(R.id.progress_bar)
        recyclerview = view?.findViewById(R.id.recyclerview)
        bindUI()
    }


    private fun bindUI() = Coroutines.main {
        progressBar?.show()
        viewModel.weatherList.await().observe(this, Observer {
            progressBar?.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(weatherList: List<WeatherViewItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(weatherList)
        }
        recyclerview?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    private fun List<CityWeatherItem>.toQuoteItem(): List<WeatherViewItem> {
        return this.map {
            WeatherViewItem(it)
        }
    }

}
