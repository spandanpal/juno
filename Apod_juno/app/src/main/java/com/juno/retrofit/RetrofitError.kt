package com.juno.retrofit

import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitError {
    companion object {
        @JvmStatic
        fun codeToErrorMessage(code: Int): String {
            return if (code >= 400 && code < 500) "Server Issue" else if (code >= 400 && code < 500) "Server Issue" else "Unresolve Server Issue"
        }

        fun showErrorMessage(error: Throwable): String? {
            return if (error is SocketTimeoutException) {
                "No Internet Connection"
            } else if (error is UnknownHostException) {
                "Server Issue"
            } else if (error is HttpException) {
                val code: Int = (error as HttpException).code()
                codeToErrorMessage(code)
            } else {
                "Unresolve Server Issue"
            }
        }
    }
}