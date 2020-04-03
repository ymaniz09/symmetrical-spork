# symmetrical-spork
This project follows the course [Model-View-Intent (MVI) Architecture](https://codingwithmitch.com/courses/model-view-intent-mvi-architecture/)
and will implement an Android project using MVI.

## dataState and viewState
When you fire off and event like get user event, this event will be
handled by the method setStateEvent inside MainViewMode. It'll trigger  
fetching the data. The response of this event will be returned
as a LiveData thru the viewState.
The fragment observing it will handle the answer and show it properly
