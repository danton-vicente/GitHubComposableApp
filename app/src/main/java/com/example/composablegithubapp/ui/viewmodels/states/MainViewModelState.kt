package com.example.composablegithubapp.ui.viewmodels.states

import com.example.composablegithubapp.domain.model.RepositoriesResponse

data class MainViewModelState(
    val list: ArrayList<RepositoriesResponse> = arrayListOf(),
    val error: String? = null,
    val screenStatus: MainScreenViewState = MainScreenViewState.LOADING
)

enum class MainScreenViewState {
    LOADING,
    NEW_PAGE_LOADING,
    SUCCESS,
    ERROR,
    NEW_PAGE_ERROR
}
