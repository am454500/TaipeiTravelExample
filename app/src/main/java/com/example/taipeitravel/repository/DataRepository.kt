/*
 *
 *  Created by paulz on 2023/6/7 上午12:21
 *  Last modified 2023/6/7 上午12:21
 */

package com.example.taipeitravel.repository

import com.example.taipeitravel.api.ApiClient
import com.example.taipeitravel.api.Resource
import com.example.taipeitravel.model.Attraction.Attraction
import com.example.taipeitravel.model.Attraction.AttractionsResponse
import retrofit2.Response

class DataRepository {
    companion object {
        var currentAttraction: Attraction? = null
    }

    /**
     * 取得景點列表
     * @param languageCode 語系代碼
     * @param page 頁數 預設1(1頁30筆)
     */
    suspend fun getAttractionList(languageCode:String, page: String = "1"): Resource<List<Attraction>> {
        return handleGetAttractionList(
            response = ApiClient().apiService.getAttractions(language = languageCode, page = page)
        )
    }

    /**
     * 處理景點api的回傳
     */
    private fun handleGetAttractionList(response: Response<AttractionsResponse>): Resource<List<Attraction>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.data)
            } ?: return Resource.Error("Attractions not found")
        }
        return Resource.Error(response.message(), response.code())
    }

}