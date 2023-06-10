/*
 *
 *  Created by paulz on 2023/6/10 上午8:58
 *  Last modified 2023/6/8 下午4:49
 */

package com.example.taipeitravel.model.Attraction

data class Attraction(
    val address: String,
    val category: List<Category>,
    val distric: String,
    val elong: Double,
    val email: String,
    val facebook: String,
    val fax: String,
    val files: List<Any>,
    val friendly: List<Any>,
    val id: Int,
    val images: List<Image>,
    val introduction: String,
    val links: List<Any>,
    val modified: String,
    val months: String,
    val name: String,
    val name_zh: String?,
    val nlat: Double,
    val official_site: String,
    val open_status: Int,
    val open_time: String,
    val remind: String,
    val service: List<Service>,
    val staytime: String,
    val target: List<Target>,
    val tel: String,
    val ticket: String,
    val url: String,
    val zipcode: String
)