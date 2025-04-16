package com.manbirkakkar.countrylistandroid

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manbirkakkar.countrylistandroid.di.Injector
import com.manbirkakkar.countrylistandroid.ui.MainActivity
import com.manbirkakkar.countrylistandroid.ui.MainViewModel
import com.manbirkakkar.network.RetrofitClient
import com.manbirkakkar.network.repository.CountryRepository
import com.manbirkakkar.network.repository.CountryRepositoryImpl
import com.manbirkakkar.network.service.ApiService

class App : Application(), Injector {

    private val retrofitClient: RetrofitClient by lazy { RetrofitClient }

    private val apiService: ApiService by lazy {
        retrofitClient.apiService
    }

    val countryRepository: CountryRepository by lazy {
        CountryRepositoryImpl(apiService)
    }

    override fun inject(mainActivity: MainActivity) {
        mainActivity.viewModel = ViewModelProvider(
            mainActivity,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(countryRepository) as T
                }
            }
        )[MainViewModel::class.java]

    }
}