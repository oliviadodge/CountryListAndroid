package com.manbirkakkar.countrylistandroid.di

import com.manbirkakkar.countrylistandroid.ui.MainActivity

interface Injector {
    fun inject(mainActivity: MainActivity)
}