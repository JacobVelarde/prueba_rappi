package com.jacob.pruebarappi.network

import com.google.gson.annotations.SerializedName

class ServiceError constructor(
    @SerializedName("code") var code: String?,
    @SerializedName("message") val message: String?,
    var url: String = "",
    var refreshing: Boolean = false,
    @SerializedName("errors") val errors: HashMap<String, String>? = null
)