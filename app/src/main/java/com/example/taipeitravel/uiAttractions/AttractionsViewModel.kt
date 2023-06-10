/*
 *
 *  Created by paulz on 2023/6/7 上午12:49
 *  Last modified 2023/6/7 上午12:49
 */

package com.example.taipeitravel.uiAttractions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taipeitravel.api.Resource
import com.example.taipeitravel.enum.EnumLanguage
import com.example.taipeitravel.model.Attraction.Attraction
import com.example.taipeitravel.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AttractionsViewModel : ViewModel() {
    private val _attractionsLiveData by lazy { MutableLiveData<Resource<List<Attraction>>>() }
    val attractionLiveData get() = _attractionsLiveData
    private val _languageLiveData by lazy { MutableLiveData<EnumLanguage>() }
    val languageLiveData get() = _languageLiveData
    private val dataRepository = DataRepository()
    init {
        languageLiveData.value = EnumLanguage.ZH_TW
    }
    /**
     * 取得景點列表
     * @param language 語系
     * @param page 頁數參數 預設第一頁 每頁30筆
     */
    fun fetchFeedAttractions(languageCode: String, page: String = "1") {
        _attractionsLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataRepository.getAttractionList(languageCode = languageCode, page = page)
                    .let { resource ->
                        withContext(Dispatchers.Main) {
                            _attractionsLiveData.value = if (resource is Resource.Success) {
                                resource
                            } else {
                                Resource.Error("取得資料失敗")
                            }
                        }

                    }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _attractionsLiveData.value = Resource.Error(e.toString())
                }
            }
        }
    }
}