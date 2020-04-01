package com.github.ymaniz09.symmetricalspork.ui.main.state

import com.github.ymaniz09.symmetricalspork.model.BlogPost
import com.github.ymaniz09.symmetricalspork.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)