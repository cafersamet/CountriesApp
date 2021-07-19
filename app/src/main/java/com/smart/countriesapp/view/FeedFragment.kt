package com.smart.countriesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.countriesapp.R
import com.smart.countriesapp.adapter.CountryAdapter
import com.smart.countriesapp.viewmodel.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_feed.*

/**
 * A simple [Fragment] subclass.
 * Use the [FeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModels()
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshData()

        countryRecyclerView.layoutManager = LinearLayoutManager(context)
        countryRecyclerView.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            countryRecyclerView.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshFromAPI()
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, { countries ->
            countries?.let {
                countryRecyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if(it){
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, {loading ->
            loading?.let {
                if(it){
                    countryLoading.visibility = View.VISIBLE
                    countryRecyclerView.visibility = View.GONE
                    countryError.visibility = View.GONE
                }
                else {
                    countryLoading.visibility = View.GONE
                }
            }
        })
    }
}