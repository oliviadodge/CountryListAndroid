package com.manbirkakkar.countrylistandroid

import com.manbirkakkar.network.model.Country
import com.manbirkakkar.network.service.ApiService

class TestApiService: ApiService {
    override suspend fun getCountries(): List<Country> {
        return listOf(Country("name", "region", "code", "capital"))
    }
}