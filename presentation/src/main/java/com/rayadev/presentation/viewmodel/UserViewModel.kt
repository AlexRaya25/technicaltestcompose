package com.rayadev.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayadev.data.local.UserDao
import com.rayadev.data.local.entity.PaginationInfo
import com.rayadev.data.mapper.toDomain
import com.rayadev.data.mapper.toEntity
import com.rayadev.domain.model.User
import com.rayadev.domain.usecase.GetUserUseCase
import com.rayadev.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val userDao: UserDao,
    private val application: Application
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _isGridView = MutableStateFlow(false)
    val isGridView: StateFlow<Boolean> get() = _isGridView

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> = _errorState.asStateFlow()

    private val perPage = 6
    private var totalPages = 1

    init {
        loadPaginationInfoFromRoom()
        loadUsersFromRoomOrApi(_currentPage.value)
    }

    private fun loadPaginationInfoFromRoom() {
        viewModelScope.launch {
            val paginationInfo = userDao.getPaginationInfo()
            totalPages = paginationInfo?.totalPages ?: 1
        }
    }

    private fun loadUsersFromRoomOrApi(page: Int) {
        viewModelScope.launch {
            val localUsers = userDao.getPagedUsers((page - 1) * perPage, perPage).map { it.toDomain() }

            if (localUsers.isNotEmpty()) {
                _users.value = localUsers
            } else {
                loadUsers(page)
            }
        }
    }

    private fun loadUsers(page: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                withTimeout(TimeUnit.SECONDS.toMillis(8)) {
                    val userResponse = getUserUseCase(page, perPage)

                    if (userResponse.isSuccess) {
                        val response = userResponse.getOrNull()
                        _users.value = response?.users ?: emptyList()
                        totalPages = response?.totalPages ?: 1
                        _currentPage.value = page
                        _errorState.value = ""

                        saveUsersToRoom(response?.users ?: emptyList())
                        savePaginationInfoToRoom(response?.totalPages ?: 1)
                    } else {
                        Log.e("API", "API response failure: ${userResponse.exceptionOrNull()}")
                        throw Exception("API response failure")
                    }
                }
            } catch (e: Exception) {
                _errorState.value = when (e) {
                    is java.net.UnknownHostException -> application.getString(R.string.error_no_internet)
                    is java.util.concurrent.TimeoutException -> application.getString(R.string.error_timeout)
                    else -> application.getString(R.string.error_loading_data)
                }

                _users.value = userDao.getPagedUsers((page - 1) * perPage, perPage).map { it.toDomain() }
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun saveUsersToRoom(users: List<User>) {
        viewModelScope.launch {
            val userEntities = users.map { it.toEntity() }
            userDao.insertUsers(userEntities)
        }
    }

    private fun savePaginationInfoToRoom(totalPages: Int) {
        viewModelScope.launch {
            val paginationInfo = PaginationInfo(totalPages = totalPages)
            userDao.insertPaginationInfo(paginationInfo)
        }
    }

    fun refreshUsers() {
        val page = _currentPage.value
        if (_isLoading.value) return
        _isLoading.value = true

        viewModelScope.launch {
            try {
                withTimeout(TimeUnit.SECONDS.toMillis(8)) {
                    val userResponse = getUserUseCase(page, perPage)

                    if (userResponse.isSuccess) {
                        val response = userResponse.getOrNull()
                        _users.value = response?.users ?: emptyList()
                        totalPages = response?.totalPages ?: 1
                        _currentPage.value = page
                        _errorState.value = ""

                        saveUsersToRoom(response?.users ?: emptyList())
                        savePaginationInfoToRoom(response?.totalPages ?: 1)
                    } else {
                        throw Exception("API response failure")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is java.net.UnknownHostException -> application.getString(R.string.error_no_internet)
                    is java.util.concurrent.TimeoutException -> application.getString(R.string.error_timeout)
                    else -> application.getString(R.string.error_refreshing_data)
                }
                _errorState.value = errorMessage
                Log.e("Snackbar", "Error during refresh: $errorMessage")

                _users.value = userDao.getPagedUsers((page - 1) * perPage, perPage).map { it.toDomain() }
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getUserById(userId: Int): Flow<User?> {
        return userDao.getUserById(userId)
            .map { it?.toDomain() ?: User.default() }
    }

    fun nextPage() {
        if (_currentPage.value < totalPages) {
            _currentPage.value += 1
            loadUsersFromRoomOrApi(_currentPage.value)
        }
    }

    fun previousPage() {
        if (_currentPage.value > 1) {
            _currentPage.value -= 1
            loadUsersFromRoomOrApi(_currentPage.value)
        }
    }

    fun canGoToNextPage(): Boolean = _currentPage.value < totalPages

    fun canGoToPreviousPage(): Boolean = _currentPage.value > 1

    fun toggleView() {
        _isGridView.value = !_isGridView.value
    }
}
