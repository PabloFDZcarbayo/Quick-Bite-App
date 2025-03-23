package com.example.quickbite.Core.Firebase


import android.net.Uri
import android.util.Log
import com.example.quickbite.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    internal val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    /* Funcion con la que hacemos login con Firebase Auth, manda el email y la contraseña
    y comprueba que exista, por eso el resultado es un FirebaseUser que puede ser null */
    suspend fun signIn(email: String, password: String): Result<FirebaseUser?> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(firebaseAuth.currentUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    //Este metodo creara un nuevo usuario con email y contraseña utilizando el metodo de firebase createUserWithEmailAndPassword
    suspend fun signUp(
        username: String,
        email: String,
        password: String,
        profileImage: Uri?
    ): Result<FirebaseUser?> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            user?.let {
                val userData = User(
                    id = user.uid,
                    username = username,
                    email = email,
                    image = profileImage?.toString() ?: ""
                )
                firestore.collection("users").document(user.uid).set(userData).await()
                Result.success(user)
            } ?: Result.failure(Exception("User creation failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveUser(user: User): Boolean {
        return try {
            firestore.collection("users").document(user.id).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUser(id: String): Result<User?> {
        return try {
            val document = firestore.collection("users").document(id).get().await()
            if (document.exists()) {
                val user = document.toObject(User::class.java)
                Log.d("FirebaseRepository", "Document exists, user: $user")
                Result.success(user)
            } else {
                Log.d("FirebaseRepository", "Document does not exist for ID: $id")
                Result.failure(Exception("User document not found"))
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error getting user document: ${e.message}", e)
            Result.failure(e)
        }
    }

    //Metodo para que el usuario cierre sesion
    fun signOut() = firebaseAuth.signOut()
}

