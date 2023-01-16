package com.example.nyc_school_challenges.network

import com.example.nyc_school_challenges.model.HighSchool
import com.example.nyc_school_challenges.model.SATScore
import retrofit2.Call
import retrofit2.http.GET

interface NYCOpenDataAPI {

    @GET("s3k6-pzi2.json")
    suspend fun highSchools(): List<HighSchool>

    @GET("f9bf-2cp4.json")
    suspend fun satScores(): List<SATScore>

//    @GET("s3k6-pzi2.json")
//    fun highSchools(): Call<List<HighSchool>>
//
//    @GET("f9bf-2cp4.json")
//    fun satScores(): Call<List<SATScore>>
}