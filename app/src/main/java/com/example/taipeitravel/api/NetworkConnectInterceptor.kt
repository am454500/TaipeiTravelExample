/*
 *
 *  Created by Paul
 *
 *
 */

package com.example.taipeitravel.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 無網路的回應
 */
class NetworkConnectInterceptor :Interceptor{
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        }catch (e:Throwable){
            throw NetworkConnectException()
        }
    }

}

class NetworkConnectException : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = "No Internet Connection"
    // You can send any message whatever you want from here.
}