package com.example.simpleweatherapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.util.Coroutines
import com.example.simpleweatherapp.util.hide
import com.example.simpleweatherapp.util.show
import com.example.simpleweatherapp.viewmodel.WeatherViewModel
import com.example.simpleweatherapp.viewmodel.WeatherViewModelFactory
import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxrelay3.BehaviorRelay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class AddNewCityFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()

    private lateinit var viewModel: WeatherViewModel
    private var progressBar: ProgressBar? = null
    private var enterCityNameET: EditText? = null
    private var addBtn: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_new_city_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        init()
        bindUI()
    }

    private fun init() {
        progressBar = view?.findViewById(R.id.progress_bar)
        enterCityNameET = view?.findViewById(R.id.enter_city_name_et)
        addBtn = view?.findViewById(R.id.add_btn)
    }


    private fun bindUI() = Coroutines.main {
        manageEditText()
        manageSuccessResponse()
        manageErrorResponse()
        handleAddBtn()
    }

    private fun handleAddBtn() {
        addBtn?.setOnClickListener {
            progressBar?.show()
            GlobalScope.launch {
                viewModel.addNewCity("london")
            }
        }
    }

    private suspend fun manageErrorResponse() {
        viewModel.errorException.await().observe(this, {
            progressBar?.hide()
            showErrorDialog(it)
        })
    }

    private suspend fun manageSuccessResponse() {
        viewModel.cityWeatherItem.await().observe(this, {
            progressBar?.hide()
            showSuccessDialog()
        })
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.stat_sys_download_done)
            .setTitle(R.string.success)
            .setMessage(R.string.city_added_successfully)
            .setPositiveButton(R.string.cancel) { dialogInterface, _ ->
                dialogInterface.dismiss()
                val navController =
                    activity?.findNavController(R.id.nav_host_fragment)
                navController?.navigateUp()
            }
            .show()
    }

    private fun showErrorDialog(it: String?) {
        AlertDialog.Builder(requireContext())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.error)
            .setMessage(it)
            .setPositiveButton(
                R.string.dismiss
            ) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun manageEditText() {
        enterCityNameET?.doOnTextChanged { text, _, _, _ ->
            addBtn?.isEnabled = text?.isNotEmpty() == true
            addBtn?.isClickable = text?.isNotEmpty() == true
        }
    }


    fun bindEditTextFillData(
        editText: EditText,
        behavior: BehaviorRelay<String?>
    ): Subscription? {
        if (behavior.value != null) {
            editText.setText(behavior.value)
        }
        return rxTextViewObservable(editText)!!
            .subscribe { t ->
                behavior.accept(
                    t.trim().toString()
                )
            }
    }

    private fun rxTextViewObservable(view: TextView): Observable<CharSequence>? {
        return RxTextView.textChanges(view).skip(1)
            .debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
