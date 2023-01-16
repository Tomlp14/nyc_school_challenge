package com.example.nyc_school_challenges.repo


import com.example.nyc_school_challenges.model.HighSchool
import com.example.nyc_school_challenges.model.SATScore
import com.example.nyc_school_challenges.model.SchoolModel
import com.example.nyc_school_challenges.model.State
import com.example.nyc_school_challenges.network.NYCOpenDataAPI
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.net.URL

class SchoolsRepository(private val api: NYCOpenDataAPI) {

    private var highSchools: List<HighSchool>? = null
    private var satScores: List<SATScore>? = null

//    fun getHighSchools(callback : SchoolsCallback){
//        val url = "s3k6-pzi2.json"
//        val fetchBody = fetch(url)
//        try {
//            highSchools = Gson().fromJson(fetchBody, Array<HighSchool>::class.java).toList()
//            val result = fetchSchools(highSchools, satScores)
//            if(!result.isEmpty()){
//                callback.onSuccess(result)
//            }
//        }
//        catch (e: Exception){
//            callback.onFailure(e)
//        }
//
//    }

//    fun fetch(url : String) : String {
//        val url = URL("https://data.cityofnewyork.us/resource/$url")
//        val json = url.readText()
//        return json
//    }
//
//    fun getSATScores(callback : SchoolsCallback){
//        val url = "f9bf-2cp4.json"
//        val fetchBody = fetch(url)
//        try {
//            satScores = Gson().fromJson(fetchBody, Array<SATScore>::class.java).toList()
//            val result = fetchSchools(highSchools, satScores)
//            if(!result.isEmpty()){
//                callback.onSuccess(result)
//            }
//        }
//        catch (e: Exception){
//            callback.onFailure(e)
//        }
//    }

    fun fetchSchools(highSchools: List<HighSchool>?, satScores: List<SATScore>?): List<SchoolModel> {
        if(highSchools == null || satScores == null) return listOf()
        val schoolsMap = mutableMapOf<String, SATScore>()
        for (satScore in satScores) {
            schoolsMap[satScore.dbn ?: ""] = satScore
        }
        return highSchools.map {
            SchoolModel(school = it, satScores = schoolsMap[it.dbn])
        }
    }

    suspend fun getSchoolModel(callback : SchoolsCallback){
        try {
            highSchools = api.highSchools()
            satScores = api.satScores()
            val result = fetchSchools(highSchools, satScores)
            if(result.isNotEmpty()){
                callback.onSuccess(result)
            }
        }
        catch (e: Exception){
            callback.onFailure(e)
        }
    }
    interface SchoolsCallback {
        fun onSuccess(schools: List<SchoolModel>)
        fun onFailure(e: Throwable)
    }
}
