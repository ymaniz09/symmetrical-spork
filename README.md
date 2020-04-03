# symmetrical-spork
This project follows the course [Model-View-Intent (MVI) Architecture](https://codingwithmitch.com/courses/model-view-intent-mvi-architecture/)
and will implement an Android project using MVI.
We'll build an event driven architecture leveraging the MVVM architecture.

## Motivation
This architecture pays off when you share the same ViewModel between
multiple fragments for instance. It'd be a nightmare keeping multiple  
LiveData for each view.

##  The app uses the following libraries / topics:
- Kotlin
- Coroutines
- Retrofit2
- Glide
- ViewModels
- Repository pattern
- NetworkBoundResource (as recommend by architecture guide in google sample. See [here](https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt) ).

The app does two things:

- Get 'User' data from [open-api.xyz/placeholder/user](https://open-api.xyz/placeholder/user).
- Get a list of 'BlogPost' data from [open-api.xyz/placeholder/blogs](https://open-api.xyz/placeholder/blogs).

## Possible events:
The two possible actions here ir get user and get blog posts.
Those two will be handled by MainStateEvent that has three possible events:
- GetBlogPostsEvent
- GetUserEvent
- None

For a big project we'd have a StateEvent for each ViewModel and a ViewState
for each

## dataState and viewState
When you fire off and event like get user event, this event will be
handled by the method setStateEvent inside MainViewMode.
The switchMap from dataState will trigger fetching the data in the
repository. The response of this event will be  returned as a LiveData
thru the viewState in the ViewModel.
The fragment observing it will handle the answer and show it properly.