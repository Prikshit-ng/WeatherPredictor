package com.prikshit.weatherdemo.ui.dash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.prikshit.weatherdemo.R
import com.prikshit.weatherdemo.ui.adapter.WeatherAdapter
import com.prikshit.weatherdemo.ui.factory.ViewModelFactory
import com.prikshit.weatherdemo.ui.viewmodel.WeatherViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.weather_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class WeatherFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private var viewModel: WeatherViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_fragment, container, false).apply {
            findViewById<RecyclerView>(R.id.recycler).layoutManager = LinearLayoutManager(activity)
            findViewById<RecyclerView>(R.id.recycler).adapter = WeatherAdapter()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, viewModelFactory).get(WeatherViewModel::class.java).apply {
            getWeatherData().observe(viewLifecycleOwner, Observer { it ->
                if(it.isEmpty()) return@Observer
                it[0].let { we->
                    Date(we.sunRise.toLong()*1000).let{dt->
                        sunriseTV.text = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(dt)
                    }
                    Date(we.sunSet.toLong()*1000).let{dt->
                        sunsetTV.text = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(dt)
                    }
                    desc.text = we.desc
                    city.text = we.cityName
                    temp.text = "${we.temp}${getString(R.string.degree)}"
                    val formatter = SimpleDateFormat("E, dd", Locale.ENGLISH)
                    Date(we.date.toLong()*1000).let{dt->
                        date.text = formatter.format(dt)
                    }

                    Glide.with(this@WeatherFragment)
                        .load("https://openweathermap.org/img/wn/${we.icon}@2x.png")
                        .apply(RequestOptions().override(50, 50))
                        .into(iconIV)
                }
                (recycler.adapter as WeatherAdapter).setData(it.filter { c->
                    val cal = Calendar.getInstance()
                    cal.time = Date(c.date.toLong()*1000)
                    val hours = cal.get(Calendar.HOUR_OF_DAY)
                    Log.e("PK", hours.toString())
                    hours == 11
                })
            })
        }
    }

}
