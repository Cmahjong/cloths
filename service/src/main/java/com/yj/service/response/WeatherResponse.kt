package com.yj.demo.service.response
import com.google.gson.annotations.SerializedName


/**
 * desc:
 * time: 2018/11/29
 * @author yinYin
 */

data class WeatherResponse(
		@SerializedName("HeWeather6") var heWeather6: List<HeWeather6?>?
)

data class HeWeather6(
		@SerializedName("basic") var basic: Basic?,
		@SerializedName("update") var update: Update?,
		@SerializedName("status") var status: String?,
		@SerializedName("now") var now: Now?
)

data class Basic(
		@SerializedName("cid") var cid: String?,
		@SerializedName("location") var location: String?,
		@SerializedName("parent_city") var parentCity: String?,
		@SerializedName("admin_area") var adminArea: String?,
		@SerializedName("cnty") var cnty: String?,
		@SerializedName("lat") var lat: String?,
		@SerializedName("lon") var lon: String?,
		@SerializedName("tz") var tz: String?
)

data class Update(
		@SerializedName("loc") var loc: String?,
		@SerializedName("utc") var utc: String?
)

data class Now(
		@SerializedName("cloud") var cloud: String?,
		@SerializedName("cond_code") var condCode: String?,
		@SerializedName("cond_txt") var condTxt: String?,
		@SerializedName("fl") var fl: String?,
		@SerializedName("hum") var hum: String?,
		@SerializedName("pcpn") var pcpn: String?,
		@SerializedName("pres") var pres: String?,
		@SerializedName("tmp") var tmp: String?,
		@SerializedName("vis") var vis: String?,
		@SerializedName("wind_deg") var windDeg: String?,
		@SerializedName("wind_dir") var windDir: String?,
		@SerializedName("wind_sc") var windSc: String?,
		@SerializedName("wind_spd") var windSpd: String?
)