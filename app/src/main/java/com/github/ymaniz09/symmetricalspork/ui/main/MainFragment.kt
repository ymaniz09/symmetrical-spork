package com.github.ymaniz09.symmetricalspork.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.ymaniz09.symmetricalspork.R
import com.github.ymaniz09.symmetricalspork.ui.DataStateListener
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainStateEvent
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var dataStateHandler: DataStateListener

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            // Handling loading and message
            dataStateHandler.onDataStateChange(dataState)

            // Handle Data<T> if it wasn't consumed
            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let {
                        viewModel.setBlogListData(it)
                    }

                    mainViewState.user?.let {
                        viewModel.setUser(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let { blogPosts ->
                // set blog post to recyclerview
                println("Posts received: $blogPosts")
            }

            viewState.user?.let {
                // set user data to view
                println("User received: $it")
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement DataStateListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        subscribeObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionGetUser -> triggerGetUserEvent()
            R.id.actionGetBlogs -> triggerGetBlogPostsEvent()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogPostsEvent() {
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    companion object {
        const val TAG = "MainFragment"
    }
}