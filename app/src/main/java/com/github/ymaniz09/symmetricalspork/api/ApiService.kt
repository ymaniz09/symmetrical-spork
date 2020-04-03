package com.github.ymaniz09.symmetricalspork.api

import androidx.lifecycle.LiveData
import com.github.ymaniz09.symmetricalspork.model.BlogPost
import com.github.ymaniz09.symmetricalspork.model.User
import com.github.ymaniz09.symmetricalspork.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>
}