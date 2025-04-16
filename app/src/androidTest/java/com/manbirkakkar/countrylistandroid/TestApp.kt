package com.manbirkakkar.countrylistandroid

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manbirkakkar.countrylistandroid.di.Injector
import com.manbirkakkar.countrylistandroid.ui.MainActivity
import com.manbirkakkar.countrylistandroid.ui.MainViewModel
import com.manbirkakkar.network.repository.CountryRepository
import com.manbirkakkar.network.repository.CountryRepositoryImpl
import com.manbirkakkar.network.service.ApiService

class TestApp : Application(), Injector {

    private val testApiService: ApiService by lazy {
        TestApiService()
    }

    val countryRepository: CountryRepository by lazy {
        CountryRepositoryImpl(testApiService)
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