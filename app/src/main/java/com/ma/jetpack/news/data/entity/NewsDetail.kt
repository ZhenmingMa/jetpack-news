package com.ma.jetpack.news.data.entity

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class NewsDetail(

	@field:SerializedName("cover")
	val cover: String? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("docid")
	val docid: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("ptime")
	val ptime: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)