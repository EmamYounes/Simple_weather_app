package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.viewmodel.WeatherViewModel
import com.example.simpleweatherapp.viewmodel.WeatherViewModelFactory
import com.squareup.picasso.Picasso
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class WeatherInfoFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel
    private var info: TextView? = null
    private var weatherIcon: ImageView? = null
    private var description: TextView? = null
    private var temp: TextView? = null
    private var humidity: TextView? = null
    private var windspeed: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        bindUI()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        info = view?.findViewById(R.id.info)
        weatherIcon = view?.findViewById(R.id.weather_icon)
        description = view?.findViewById(R.id.description)
        temp = view?.findViewById(R.id.temp)
        humidity = view?.findViewById(R.id.humidity)
        windspeed = view?.findViewById(R.id.windspeed)
    }


    private fun bindUI() {
        setData()
    }

    override fun onResume() {
        super.onResume()
        setTitle()
        showBackBtn()
    }

    private fun showBackBtn() {
        (activity as HomeActivity).showBackBtn()
    }

    private fun setTitle() {
        (activity as HomeActivity).setTitle(viewModel.getSelectedItemData()?.cityName)
    }

    private fun setData() {
        val item = viewModel.getSelectedItemData()
        info?.text = "Weather information for " + item?.cityName + " received on " + item?.date
        description?.text = item?.description
        temp?.text = item?.temp + "C"
        humidity?.text = item?.humidity + "%"
        windspeed?.text = item?.windSpeed + "kmh"
        setIcon(item)
    }

    private fun setIcon(item: CityWeatherItem?) {
        Picasso.get()
            .load(item?.weatherImage).resize(
                200, 200
            )
            .error(R.drawable.ic_browser_error_svgrepo_com)
            .into(weatherIcon)
    }

    override fun onPause() {
        super.onPause()
        hideBackBtn()
    }

    private fun hideBackBtn() {
        (activity as HomeActivity).hideBackBtn()
    }

}
