package com.jacob.pruebarappi.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacob.pruebarappi.network.Constants
import com.jacob.pruebarappi.network.ServiceError
import com.jacob.pruebarappi.network.ServiceException
import com.jacob.pruebarappi.network.SessionException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    /**
     * LiveData to notify a activity/fragment when service has an error
     */
    val error: MutableLiveData<ServiceError> = MutableLiveData()

    /**
     * LiveData to notify a activity/fragment when service is start/end to block UI if necessary
     */
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    fun executeService(loader: Boolean = true, service: suspend () -> Unit) {
        loading.postValue(loader)
        GlobalScope.launch {
            coroutineScope {
                try {
                    service()
                } catch (ex: ServiceException) {
                    ex.printStackTrace()
                    error.postValue(ex.error)
                } catch (ex: SessionException) {
                    Log.e("ERROR", ex.message ?: "SessionException")
                    throw ex
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    error.postValue(ServiceError(Constants.INTERNAL_ERROR_CODE, ex.message ?: ""))
                }
                loading.postValue(false)
            }
        }
    }

    private fun doOnError(ex: Throwable) {
        error.postValue(ServiceError(Constants.INTERNAL_ERROR_CODE, ex.message ?: "", "", false))
        loading.postValue(false)
    }
}