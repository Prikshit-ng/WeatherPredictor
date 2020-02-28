package com.prikshit.weatherdemo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.prikshit.weatherdemo.R
import com.prikshit.weatherdemo.local.model.WeatherEntity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherAdapter:RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var todoList = ArrayList<WeatherEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.item_weather, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var we = todoList[position]
        holder.itemView.findViewById<TextView>(R.id.temp).text = "${we.temp}${holder.itemView.context.getString(R.string.degree)}"

        val formatter = SimpleDateFormat("E, dd", Locale.ENGLISH)
        Date(we.date.toLong()*1000).let{ dt->
            holder.itemView.findViewById<TextView>(R.id.date).text =  formatter.format(dt)
        }
        Glide.with(holder.itemView.context)
            .load("https://openweathermap.org/img/wn/${we.icon}@2x.png")
            .apply(RequestOptions().override(50, 50))
            .into(holder.itemView.findViewById(R.id.iconIV))
    }

    fun setData(weatherList:List<WeatherEntity>){
        val diffCallback = TodoDiffCallback(this.todoList, weatherList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.todoList.clear()
        this.todoList.addAll(weatherList)
        diffResult.dispatchUpdatesTo(this)
    }

    class TodoDiffCallback(private val oldList:List<WeatherEntity>, private val newList: List<WeatherEntity>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].date == newList[newItemPosition].date)
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition] == newList[newItemPosition])
        }

    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root)
}
