package com.github.ymaniz09.symmetricalspork.ui

import com.github.ymaniz09.symmetricalspork.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}