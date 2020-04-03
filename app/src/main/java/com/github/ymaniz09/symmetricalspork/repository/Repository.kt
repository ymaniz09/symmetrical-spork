package com.github.ymaniz09.symmetricalspork.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.ymaniz09.symmetricalspork.api.RetrofitBuilder
import com.github.ymaniz09.symmetricalspork.model.BlogPost
import com.github.ymaniz09.symmetricalspork.model.User
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainViewState
import com.github.ymaniz09.symmetricalspork.util.*

object Repository {
    fun getBlogPosts() : LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPosts()
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

        }.asLiveData()
    }

    fun getUser(userId: String) : LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

        }.asLiveData()
    }
}