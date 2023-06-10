/*
 *
 *  Created by paulz on 2023/6/10 上午8:58
 *  Last modified 2023/6/7 上午12:32
 */

package com.example.taipeitravel.model.Attraction

data class AttractionsResponse(
    val data: List<Attraction>,
    val total: Int
)