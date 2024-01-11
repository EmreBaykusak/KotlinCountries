package com.example.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincountries.databinding.FragmentCountryBinding
import com.example.kotlincountries.util.downloadFromUrl
import com.example.kotlincountries.util.placeholderProgressBar
import com.example.kotlincountries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryViewModel

    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        observeLiveData()

        viewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        viewModel.getDataFromRoom(countryUuid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner){country ->
            country?.let {
                binding.countryDetailsName.text = country.countryName
                binding.countryDetailsRegion.text = country.countryRegion
                binding.countryDetailsCapital.text = country.countryCapital
                binding.countryDetailsCurrency.text = country.countryCurrency
                binding.countryDetailsLanguage.text = country.countryLanguage
                context?.let {
                    binding.countryDetailsImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }
            }
        }
    }
}