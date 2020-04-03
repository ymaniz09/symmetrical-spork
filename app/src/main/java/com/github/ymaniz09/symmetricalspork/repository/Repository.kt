package com.github.ymaniz09.symmetricalspork.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.ymaniz09.symmetricalspork.api.RetrofitBuilder
import com.github.ymaniz09.symmetricalspork.model.BlogPost
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainViewState
import com.github.ymaniz09.symmetricalspork.util.ApiEmptyResponse
import com.github.ymaniz09.symmetricalspork.util.ApiErrorResponse
import com.github.ymaniz09.symmetricalspork.util.ApiSuccessResponse

object Repository {
    fun getBlogPosts() : LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // handle error
                            }

                            is ApiErrorResponse -> {
                                value = MainViewState() // handle empty/error
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String) : LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when(apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    user = apiResponse.body
                                )
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // handle error
                            }

                            is ApiErrorResponse -> {
                                value = MainViewState() // handle empty/error
                            }
                        }
                    }
                }
            }
    }
}