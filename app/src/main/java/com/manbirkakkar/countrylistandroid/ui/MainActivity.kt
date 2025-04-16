package com.manbirkakkar.countrylistandroid.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.manbirkakkar.base.BaseActivity
import com.manbirkakkar.countrylistandroid.adapter.CountriesAdapter
import com.manbirkakkar.countrylistandroid.databinding.ActivityMainBinding
import com.manbirkakkar.countrylistandroid.di.Injector
import com.manbirkakkar.network.model.Country
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val adapter by lazy { CountriesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as Injector).inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = this@MainActivity.adapter
        setHasFixedSize(true)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.uiState.collect { handleUiState(it) } }
            }
        }
    }

    private fun handleUiState(state: CountriesUiState) {
        when (state) {
            is CountriesUiState.Loading -> showLoading()
            is CountriesUiState.Success -> showCountries(state.countries)
            is CountriesUiState.Error -> showError(state.message)
        }
    }

    private fun showLoading() = with(binding) {
        recyclerView.isVisible = false
        errorMessage.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun showCountries(countries: List<Country>) = with(binding) {
        recyclerView.isVisible = true
        errorMessage.isVisible = false
        binding.progressBar.isVisible = false
        adapter.submitList(countries)
    }

    private fun showError(message: String?) = with(binding) {
        recyclerView.isVisible = false
        errorMessage.isVisible = true
        binding.progressBar.isVisible = false
        errorMessage.text = message ?: "Unknown error occurred"
    }
}
