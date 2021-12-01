package com.example.simpleweatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
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

class HomeFragment : Fragment(), KodeinAware, OnClick {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel
    private var recyclerview: RecyclerView? = null
    private var addViewLayout: LinearLayout? = null
    private var addIcon: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        recyclerview = view?.findViewById(R.id.recyclerview)
        addViewLayout = view?.findViewById(R.id.add_view)
        addIcon = view?.findViewById(R.id.add_icon)
        bindUI()
    }


    private fun bindUI() = Coroutines.main {
        manageWeatherList()
        handleAddIconInCaseEmptyList()
        handleMainAddIcon()
    }

    private fun handleMainAddIcon() {
        (activity as HomeActivity).getAddIconToolbar()?.setOnClickListener {
            navToAddNewCityFragment()
        }
    }

    private fun handleAddIconInCaseEmptyList() {
        addIcon?.setOnClickListener {
            navToAddNewCityFragment()
        }
    }

    private fun navToAddNewCityFragment() {
        val navController =
            activity?.findNavController(R.id.nav_host_fragment)
        navController?.navigate(R.id.addNewCityFragment)
    }

    private suspend fun manageWeatherList() {
        viewModel.weatherList.await().observe(this, {
            if (it.isEmpty()) {
                recyclerview?.visibility = View.GONE
                addViewLayout?.visibility = View.VISIBLE
                (activity as HomeActivity).hideAddIconToolbar()
            } else {
                recyclerview?.visibility = View.VISIBLE
                (activity as HomeActivity).showAddIconToolbar()
                addViewLayout?.visibility = View.GONE
                initRecyclerView(it.toWeatherViewItem())
            }
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


    private fun List<CityWeatherItem>.toWeatherViewItem(): List<WeatherViewItem> {
        return this.map {
            WeatherViewItem(it, this@HomeFragment)
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
