package com.example.quickbite.ViewModel

import android.net.Uri
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickbite.Core.Firebase.FirebaseRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    //Se utilizara para guardar el estado del usuario
    private val _userState = MutableStateFlow<FirebaseUser?>(null)
    val userState: StateFlow<FirebaseUser?> = _userState

    //Lo utilizamos para mostrar los posibles errores
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    //Verificamos si el  formato del email es correcto, y si la contraseña tiene el tamaño correcto
    fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6


    //Igual que antes pero con la ventana registro
    fun enableSingUp(username: String, email: String, password: String, confirmPassword: String) =
        Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() && username.length >= 6 && password.length >= 6 && confirmPassword.length >= 6


    //Comprueba que ambas contraseñas sean iguales
    fun checkpasswordMatch(password: String, confirmPassword: String): Boolean {
        if (password == confirmPassword)
            return true
        return false

    }

    //Funcion con que hacemos login con Firebase AUth
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            //Comprobamos que exista el usuario
            val result = firebaseRepository.signIn(email, password)
            /* En caso positivo devolvemos el usuario para indicar a la vista que
            todo esta ok y pueda navegar a la siguiente pantalla ademas de limpiar los posibles errores */
            result.onSuccess { user ->
                _userState.value = user
                _errorMessage.value = null
            }
            //Por el contrario devolvemos un error
            result.onFailure { error ->
                _errorMessage.value = when (error) {
                    is FirebaseAuthInvalidUserException -> "No account found with this email."
                    is FirebaseAuthInvalidCredentialsException -> "Incorrect email or password."
                    else -> "An unknown error occurred. Please try again."
                }
            }
        }
    }


    //Funcion para realizar el registro con Firebase Auth
    fun signUp(username: String, email: String, password: String, profilePhoto: Uri?) {
        viewModelScope.launch {
            //Hacemos el proceso de registro con email y contraeña
            val result = firebaseRepository.signUp(email, password)
            //En caso de que todo sea correcto indicamos el estado del user a la vista y limpiamos posibles errores
            result.onSuccess { user ->
                _userState.value = user
                _errorMessage.value = null

                //CAMBIAR A FIRESTORE
                //Una vez registrado el user actualizamos sus datos con el nombre y la foto
                user?.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .setPhotoUri(profilePhoto)
                        .build()
                )?.await()
            }
            //En caso de error devolvemos un error
            result.onFailure { error ->
                _errorMessage.value = when (error) {
                    is FirebaseAuthUserCollisionException-> "This email is already in use."
                    else -> "An unknown error occurred. Please try again."
                }
            }
        }
    }

    //Limpiamos los posibles errores
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
