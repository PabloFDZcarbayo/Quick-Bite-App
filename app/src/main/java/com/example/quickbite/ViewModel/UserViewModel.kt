package com.example.quickbite.ViewModel

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickbite.Core.Firebase.FirebaseRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState: StateFlow<FirebaseUser?> = _userState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6


    fun enableSingUp(username: String, email: String, password: String, confirmPassword: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && username.length >= 6 && password.length >= 6 && confirmPassword.length >= 6


    fun checkpasswordMatch(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword)
            return true
        return false

    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = firebaseRepository.signIn(email, password)
            result.onSuccess { user ->
                _userState.value = user
                _errorMessage.value = null
            }
            result.onFailure { error ->
                _errorMessage.value = when (error) {
                    is FirebaseAuthInvalidUserException -> "No account found with this email."
                    is FirebaseAuthInvalidCredentialsException -> "Incorrect email or password."
                    else -> "An unknown error occurred. Please try again."
                }
            }
        }
    }


    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
