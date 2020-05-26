package com.ma.jetpack.news.data.entity

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (
    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: T? = null
)