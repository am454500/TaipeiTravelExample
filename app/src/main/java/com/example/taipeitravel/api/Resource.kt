/*
 *
 *  Created by Paul
 *
 *
 */

package com.example.taipeitravel.api

sealed class Resource<T>(
    val data: T? = null,
    val message:String? = null,
    val statusCode: Int? = null
){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String,status:Int?=null, data: T? = null) : Resource<T>(data, message,status)
    class Loading<T> : Resource<T>()
}