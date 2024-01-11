package com.example.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.databinding.ItemCountryBinding
import com.example.kotlincountries.model.Country
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeholderProgressBar
import com.example.kotlincountries.view.FeedFragmentDirections

class CountryAdapter (val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    inner class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.countryName.text = country.countryName
            binding.countryRegion.text = country.countryRegion
            binding.countryImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(binding.root.context))
            // Bind other views as needed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        holder.bind(country)

        holder.itemView.setOnClickListener{
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCountryList(newCountryList : List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}