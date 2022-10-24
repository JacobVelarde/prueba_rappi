package com.jacob.pruebarappi.network

import retrofit2.Response
import java.net.HttpURLConnection

fun <T> Response<T>.check(): T {
    if (isSuccessful) {
        body()?.let {
            return it
        }
    } else if (code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
        throw SessionException()
    }
    throw ServiceException.factory(this)
}

fun <T> Response<T>.checkVoid(): Boolean{
    if (isSuccessful) {
        return isSuccessful
    }
    throw ServiceException.factory(this)
}