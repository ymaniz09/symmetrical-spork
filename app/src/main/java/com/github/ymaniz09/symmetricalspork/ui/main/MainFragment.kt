package com.github.ymaniz09.symmetricalspork.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.ymaniz09.symmetricalspork.R
import com.github.ymaniz09.symmetricalspork.model.BlogPost
import com.github.ymaniz09.symmetricalspork.model.User
import com.github.ymaniz09.symmetricalspork.ui.DataStateListener
import com.github.ymaniz09.symmetricalspork.ui.main.state.MainStateEvent
import com.github.ymaniz09.symmetricalspork.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_blog_list_item.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), BlogPostAdapter.Interaction {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var dataStateHandler: DataStateListener
    private lateinit var blogPostAdapter: BlogPostAdapter

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(TopSpacingItemDecoration(30))
            blogPostAdapter = BlogPostAdapter(this@MainFragment)
            adapter = blogPostAdapter
        }
    }
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
                blogPostAdapter.submitList(blogPosts)
            }

            viewState.user?.let {
                setUser(it)
            }
        })
    }

    private fun setUser(user: User) {
        email.text = user.email
        username.text = user.username

        view?.let {
            Glide.with(it)
                .load(user.image)
                .into(image)
        }
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
        initRecyclerView()
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

    override fun onItemSelected(position: Int, item: BlogPost) {
        Log.d(TAG, "Item clicked at $position")
        Log.d(TAG, "Blog item $item")
    }
}