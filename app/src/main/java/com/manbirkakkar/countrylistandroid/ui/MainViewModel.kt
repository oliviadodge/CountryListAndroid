package com.manbirkakkar.countrylistandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manbirkakkar.network.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            val result = repository.getCountries()
            result.fold(
                onSuccess = { countries ->
                    _uiState.value = CountriesUiState.Success(countries)
                },
                onFailure = { error ->
                    _uiState.value = CountriesUiState.Error(error.message)
                }
            )
        }
    }
}