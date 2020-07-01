package com.juno.retrofit

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class generic(context: Context?) {
    private var mResponseListener: DataInterface? = null
    private var mErrorListener: ErrorInterface? = null
    private val retrofit: Retrofit
    fun setOnDataListener(listener: DataInterface?) {
        mResponseListener = listener
    }

    fun setOnErrorListener(t: ErrorInterface?) {
        mErrorListener = t
    }

    interface DataInterface {
        fun responseData(responseBody: String?)
    }

    interface ErrorInterface {
        fun errorData(throwable: Throwable?)
    }

    init {
        val url = ""
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}