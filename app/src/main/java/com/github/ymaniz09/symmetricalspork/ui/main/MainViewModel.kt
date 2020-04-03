package com.github.ymaniz09.symmetricalspork.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ymaniz09.symmetricalspork.model.BlogPost
import com.github.ymaniz09.symmetricalspork.model.User
import com.github.ymaniz09.symmetricalspork.repository.Repository
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainStateEvent
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainStateEvent.*
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainViewState
import com.github.ymaniz09.symmetricalspork.util.AbsentLiveData
import com.github.ymaniz09.symmetricalspork.util.DataState

class MainViewModel : ViewModel() {
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is GetBlogPostsEvent ->
                Repository.getBlogPosts()

            is GetUserEvent ->
                Repository.getUser(stateEvent.userId)

            is None ->
                AbsentLiveData.create()
        }
    }

    fun setBlogListData(blogPosts: List<BlogPost>){
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User){
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let {
            it
        }?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent){
        _stateEvent.value = event
    }
}