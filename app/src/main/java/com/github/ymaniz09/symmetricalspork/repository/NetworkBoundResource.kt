package com.github.ymaniz09.symmetricalspork.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.github.ymaniz09.symmetricalspork.Constants
import com.github.ymaniz09.symmetricalspork.util.*
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(Dispatchers.IO) {
            delay(Constants.FAKE_NETWORK_DELAY)

            withContext(Dispatchers.Main) {
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)

                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }

            is ApiEmptyResponse -> {
                onReturnError("HTTP 204. Returned nothing")
            }

            is ApiErrorResponse -> {
                onReturnError(response.errorMessage)
            }
        }
    }

    private fun onReturnError(message: String) {
        result.value = DataState.error(message)
    }

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}