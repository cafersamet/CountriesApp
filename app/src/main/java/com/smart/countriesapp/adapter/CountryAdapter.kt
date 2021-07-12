package com.smart.countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.smart.countriesapp.R
import com.smart.countriesapp.model.Country
import com.smart.countriesapp.util.downloadFromURL
import com.smart.countriesapp.util.placeholderProgressBar
import com.smart.countriesapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(private val countryList: ArrayList<Country>):
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_country,parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
        countryList[position].imageUrl?.let { holder.view.imageView.downloadFromURL(it, placeholderProgressBar(holder.view.context)) }

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragment2ToCountryFragment2()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}