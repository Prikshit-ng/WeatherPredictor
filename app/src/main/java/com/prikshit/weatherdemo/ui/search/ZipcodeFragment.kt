package com.prikshit.weatherdemo.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.prikshit.weatherdemo.R
import com.prikshit.weatherdemo.ui.factory.ViewModelFactory
import com.prikshit.weatherdemo.ui.viewmodel.AppState
import com.prikshit.weatherdemo.ui.viewmodel.WeatherViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_zipcode.*
import javax.inject.Inject

class ZipcodeFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        fun newInstance() = ZipcodeFragment()
    }

    private var viewModel: WeatherViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_zipcode, container, false).apply {
            findViewById<View>(R.id.submitBt).setOnClickListener {
                if(findViewById<EditText>(R.id.zipET).text.toString().length<6){
                    Toast.makeText(context, context.getString(R.string.error_zip), Toast.LENGTH_SHORT).show()
                    findViewById<EditText>(R.id.zipET).error = context.getString(R.string.error_zip)
                    return@setOnClickListener
                }
                viewModel?.let {
                    it.fetchWeatherList(findViewById<EditText>(R.id.zipET).text.toString())
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(activity!!, viewModelFactory).get(WeatherViewModel::class.java).apply {
            responseStatus.observe(viewLifecycleOwner, Observer {
                when(it){
                    WeatherViewModel.Status.LOADING->{
                        showProgress()
                    }
                    WeatherViewModel.Status.ERROR->{
                        hideProgress()
                        Toast.makeText(context, "Couldn't fetch data", Toast.LENGTH_SHORT).show()
                    }
                    WeatherViewModel.Status.COMPLETED->{
                        hideProgress()
                        appStateLiveData.postValue(AppState.DASH)
                    }
                }
            })
        }
    }

    private fun showProgress() {
        submitBt.visibility = View.INVISIBLE
        pb.visibility = View.VISIBLE
    }
    private fun hideProgress() {
        submitBt.visibility = View.VISIBLE
        pb.visibility = View.GONE
    }
}
