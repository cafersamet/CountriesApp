package com.smart.countriesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.smart.countriesapp.R
import com.smart.countriesapp.util.downloadFromURL
import com.smart.countriesapp.util.placeholderProgressBar
import com.smart.countriesapp.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


/**
 * A simple [Fragment] subclass.
 * Use the [CountryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CountryFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel
    private var countryUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, {country ->
            country?.let{
                countryName.text = country.countryName
                countryRegion.text = country.countryRegion
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                context?.let {
                    countryImage.downloadFromURL(country.imageUrl, placeholderProgressBar(it))
                }
            }
        })
    }
}