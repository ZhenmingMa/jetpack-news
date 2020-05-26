package cn.sinoits.traffic.project.data.entity

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName
data class NewsList(

	@field:SerializedName("newsId")
	val newsId: String? = null,

	@field:SerializedName("postTime")
	var postTime: String? = null,

	@field:SerializedName("videoList")
	val videoList: Array<String>? = null,

	@field:SerializedName("digest")
	val digest: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("imgList")
	var imgList: List<String?>? = null
)