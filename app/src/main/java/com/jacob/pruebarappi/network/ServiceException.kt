package com.jacob.pruebarappi.network

import com.google.gson.Gson
import retrofit2.Response
import java.net.HttpURLConnection

class ServiceException private constructor() : Exception() {
    companion object {
        fun <T> factory(response: Response<T>): ServiceException {
            val url = response.toString().split("url=")[1].replace("}", "")
            val refresh = response.code() == HttpURLConnection.HTTP_UNAUTHORIZED
            return try {
                val parse = Gson().fromJson(response.errorBody()?.string(), ServiceError::class.java)
                ServiceException().apply {
                    error = parse ?: getError(
                        response.code().toString(),
                        response.message(),
                        url,
                        refresh
                    )
                    error?.code = response.code().toString()
                    error?.url = url
                    error?.refreshing = refresh
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
                ServiceException().apply {
                    error =
                        getError(
                            response.code().toString(),
                            response.message() ?: ex.message ?: "",
                            url,
                            refresh
                        )
                }
            }
        }

        private fun getError(code: String?, message: String?, url: String, refresh: Boolean) =
            ServiceError(code ?: "500", message ?: "", url, refresh)

        private fun getInternalError(message: String?) =
            ServiceError("101", message ?: "")
    }

    var error: ServiceError? = null
        internal set
}