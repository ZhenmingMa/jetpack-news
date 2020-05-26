package cn.sinoits.traffic.project.data.entity

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

data class PageInfo<T>(

	@field:SerializedName("totalPage")
	val totalPage: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null,

	@field:SerializedName("list")
	val list: List<T>? = null
)