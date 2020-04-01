package com.github.ymaniz09.symmetricalspork.di

import com.github.ymaniz09.symmetricalspork.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
}