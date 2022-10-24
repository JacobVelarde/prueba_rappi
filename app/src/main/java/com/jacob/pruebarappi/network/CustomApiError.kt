package com.jacob.pruebarappi.network

class CustomApiError(
    val code: Int,
    val error: String
    ): RuntimeException() {



    override fun getLocalizedMessage(): String? {
        return "{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}'
    }
}