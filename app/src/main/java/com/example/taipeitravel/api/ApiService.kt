/*
 *
 *  Created by Paul
 *
 *
 */

package com.example.taipeitravel.api

import com.example.taipeitravel.model.Attraction.AttractionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //region 景點
    /**
     * 取得景點列表
     * @param page 頁數 預設為1
     */
    @Headers("Accept: application/json")
    @GET("open-api/{language}/Attractions/All")
    suspend fun getAttractions(
        @Path("language") language: String = "zh-tw",
        @Query("page") page: String = "1",
    ): Response<AttractionsResponse>
    //endregion
}