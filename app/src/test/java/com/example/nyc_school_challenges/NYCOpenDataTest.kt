package com.example.nyc_school_challenges

import com.example.nyc_school_challenges.model.SchoolModel
import com.example.nyc_school_challenges.network.NYCOpenDataNetworkModule
import com.example.nyc_school_challenges.repo.SchoolsRepository
import com.example.nyc_school_challenges.repo.SchoolsRepositoryModule
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NYCOpenDataTest {
    //test repository
    private lateinit var repository: SchoolsRepository

    @Before
    fun setUp() {
        repository = SchoolsRepositoryModule.providesSchoolsRepository(
            NYCOpenDataNetworkModule.providesNYCOpenDataAPI(
                NYCOpenDataNetworkModule.providesRetrofit()
            )
        )
    }
    @Test
    fun testRepository() {
//        repository.getSchoolModel(object : SchoolsRepository.SchoolsCallback {
//
//
//            override fun onSuccess(schools: List<SchoolModel>) {
//                assert(true)
//            }
//
//            override fun onFailure(e: Throwable) {
//                assert(false)
//            }
//        })
    }


}