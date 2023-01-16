package com.example.nyc_school_challenges

import com.example.nyc_school_challenges.network.NYCOpenDataNetworkModule
import com.example.nyc_school_challenges.repo.SchoolsRepositoryModule
import com.example.nyc_school_challenges.viewmodel.SchoolViewModel
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ViewModelTest {

    private lateinit var viewModel: SchoolViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testViewModel() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            viewModel = SchoolViewModel(
                SchoolsRepositoryModule.providesSchoolsRepository(
                    NYCOpenDataNetworkModule.providesNYCOpenDataAPI(
                        NYCOpenDataNetworkModule.providesRetrofit()
                    )
                )
            )

            viewModel.fetchSchools()
            print(viewModel.lState.value)
            assertEquals( com.example.nyc_school_challenges.model.Result.SUCCESS, viewModel.lState.value?.message)

        } finally {
            Dispatchers.resetMain()
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun settingMainDispatcher() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            viewModel = SchoolViewModel(
                SchoolsRepositoryModule.providesSchoolsRepository(
                    NYCOpenDataNetworkModule.providesNYCOpenDataAPI(
                        NYCOpenDataNetworkModule.providesRetrofit()
                    )
                )
            )
            viewModel.loadMessage() // Uses testDispatcher, runs its coroutine eagerly
            assertEquals("Greetings!", viewModel.message.value)
        } finally {
            Dispatchers.resetMain()
        }
    }
}