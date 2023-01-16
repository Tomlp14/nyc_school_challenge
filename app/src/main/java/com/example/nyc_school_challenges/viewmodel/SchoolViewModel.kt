package com.example.nyc_school_challenges.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyc_school_challenges.model.Result
import com.example.nyc_school_challenges.model.SATScore
import com.example.nyc_school_challenges.model.SchoolModel
import com.example.nyc_school_challenges.model.State
import com.example.nyc_school_challenges.repo.SchoolsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SchoolViewModel @Inject constructor(val repository : SchoolsRepository) : ViewModel() {
    private val mState =  MutableStateFlow<State>(State.Ready())
    private val mSearchResult = MutableStateFlow<List<SchoolModel>>(listOf())
    private val mCurrentSelection = MutableStateFlow<SchoolModel>(SchoolModel())
    val lState: StateFlow<State>
        get() = mState
    val lSearchResult : StateFlow<List<SchoolModel>>
        get() = mSearchResult

    val lCurrentSelection : StateFlow<SchoolModel>
        get() = mCurrentSelection

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchSchools() {

        if (mState.value.message != Result.Ready) {
            return
        }
        mState.value = State.Loading()
        viewModelScope.launch {
            repository.getSchoolModel(
                object : SchoolsRepository.SchoolsCallback {
                    override fun onSuccess(schools: List<SchoolModel>) {
                        mState.value = State.Success(schools.sortedBy { it.schoolName })
                        search("")
                    }

                    override fun onFailure(e: Throwable) {
                        mState.value = State.Failure(e)
                    }
                }
            )
        }


    }

    fun select(schoolModel: SchoolModel) {
        mCurrentSelection.value = schoolModel
    }
    //search function
    fun search(query : String){
        val schools = (mState.value as? State.Success)?.schools
        if (schools != null){
            mSearchResult.value = schools.filter {
                it.schoolName.contains(query, true)
            }
        }
    }

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    fun loadMessage() {
        viewModelScope.launch {
            _message.value = "Greetings!"
        }
    }

    init {
        fetchSchools()
        search("")
    }

}

