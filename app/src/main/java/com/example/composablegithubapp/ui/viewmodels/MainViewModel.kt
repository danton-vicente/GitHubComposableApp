package com.example.composablegithubapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composablegithubapp.domain.model.RepositoriesResponse
import com.example.composablegithubapp.domain.repositories.IMainRepository
import com.example.composablegithubapp.ui.viewmodels.states.MainScreenViewState
import com.example.composablegithubapp.ui.viewmodels.states.MainViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IMainRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainViewModelState())
    val state: StateFlow<MainViewModelState> = _uiState.asStateFlow()

    fun getData(page: Int) {
        _uiState.update {
            it.copy(
                screenStatus = if (page > 1) MainScreenViewState.NEW_PAGE_LOADING else MainScreenViewState.LOADING
            )
        }
        viewModelScope.launch {
            repository.getResults(page)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            error = null,
                            list = updateList(response.repositories),
                            screenStatus = MainScreenViewState.SUCCESS
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message,
                            screenStatus = MainScreenViewState.ERROR
                        )
                    }
                }
        }
    }

    private fun updateList(newList: ArrayList<RepositoriesResponse>): ArrayList<RepositoriesResponse> {
        val updatedList = _uiState.value.list
        newList.map {
            updatedList.add(it)
        }
        return updatedList
    }
}