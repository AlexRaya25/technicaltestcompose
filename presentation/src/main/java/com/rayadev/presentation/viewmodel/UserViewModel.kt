package com.rayadev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.data.local.UserDao
import com.rayadev.data.mapper.toDomain
import com.rayadev.domain.model.User
import com.rayadev.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userDao: UserDao
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _isGridView = MutableStateFlow(false)
    val isGridView: StateFlow<Boolean> get() = _isGridView

    private val perPage = 6
    private var totalPages = 1

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    init {
        loadUsers(_currentPage.value)
    }

    private fun loadUsers(page: Int) {
        viewModelScope.launch {
            getUserUseCase(page, perPage)
                .onSuccess { userResponse ->
                    _users.value = userResponse.users
                    totalPages = userResponse.totalPages
                    _currentPage.value = page
                    _errorState.value = null
                }
                .onFailure { e ->
                    _errorState.value = "Failed to load users: ${e.message}"
                }
        }
    }

    fun getUserById(userId: Int): Flow<User?> {
        return userDao.getUserById(userId).map { it?.toDomain() }
    }

    fun nextPage() {
        if (_currentPage.value < totalPages) {
            _currentPage.value += 1
            loadUsers(_currentPage.value)
        }
    }

    fun previousPage() {
        if (_currentPage.value > 1) {
            _currentPage.value -= 1
            loadUsers(_currentPage.value)
        }
    }

    fun canGoToNextPage(): Boolean = _currentPage.value < totalPages
    fun canGoToPreviousPage(): Boolean = _currentPage.value > 1

    fun toggleView() {
        _isGridView.value = !_isGridView.value
    }

}
