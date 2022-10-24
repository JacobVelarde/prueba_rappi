package com.jacob.pruebarappi.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HttpError {

    /*public HttpError(Response<?> response) {
        super(response);
    }*/
    @SerializedName("status_code")
    @Expose
    var statusCode = 0

    @SerializedName("message")
    @Expose
    var message: String? = null
}