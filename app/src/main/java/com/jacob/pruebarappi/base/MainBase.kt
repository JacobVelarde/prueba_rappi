package com.jacob.pruebarappi.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jacob.pruebarappi.R
import com.jacob.pruebarappi.network.*
import com.jacob.pruebarappi.utils.CustomProgressDialog
import retrofit2.HttpException
import java.net.ConnectException

abstract class MainBase: AppCompatActivity() {

    private var gson = Gson()
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProgressDialog = CustomProgressDialog(this)

    }

    val showError: Observer<ServiceError> =
        Observer<ServiceError> { serviceError ->
            var errorMsg: String? = ""
            if (serviceError.message != null) {
                errorMsg = serviceError.message
            } else if (serviceError.errors != null) {
                errorMsg = serviceError.errors.toString()
            } else {
                getString(R.string.app_error)
            }

            showCodeMessageError(serviceError.code!!, errorMsg)
        }

    fun showCodeMessageError(code: String, msg: String?) {
        if (msg == null) return
        Snackbar.make(findViewById(android.R.id.content), "$code $msg", Snackbar.LENGTH_LONG).show()
    }

    fun showMessageError(msg: String?) {
        if (msg == null) return
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }

    fun showToastAlert(msg: String?) {
        if (msg == null) return
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    val loading = Observer<Boolean> { loaderVisible ->
            if (loaderVisible) {
                showLoader()
            } else {
                hideLoader()
            }
        }

    fun showLoader() {
        if (!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }
    }

    fun hideLoader() {
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    /*fun hideKeyBoard(view: View?) {
        if (view != null) {
            mInputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }*/

    fun handleApiError(exception: Throwable) {
        exception.localizedMessage?.let { Log.v("HandelApiError: ", it) }
        when (exception) {
            is ConnectException -> {
                handleNetworkError(exception)
            }
            is HttpException -> {
                handleHttpError(exception)
            }
            is CustomApiError -> {
                handleCustomError(exception)
            }
            is NoDataError -> {
                showToastAlert(getString(R.string.error_data_is_null))

            }
            else -> {
                showMessageError(getString(R.string.error_something_went_wrong))
            }
        }
    }

    private fun handleHttpError(exception: HttpException) {
        Log.v("handleHttpError called", ""+exception.code()+": "+exception.message())
        val code: Int = exception.code()
        var message = ""
        try {
            val httpError: HttpError = gson.fromJson(
                exception.response()?.errorBody()?.string() ?: "",
                HttpError::class.java
            )
            message = httpError.message.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleCustomError(exception: CustomApiError) {
        exception.localizedMessage?.let { Log.v("CustomError called: ", it) }
        showMessageError(exception.error)
    }

    private fun handleNetworkError(exception: ConnectException) {
        Log.v("handleNetworkError(): ", exception.message!!)
        showMessageError(getString(R.string.error_internet_connection))
    }

    fun replaceFragment(fragment: Fragment, containerId: Int, isAddedToBackStack: Boolean) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        if (isAddedToBackStack) {
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }
}