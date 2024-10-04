package com.example.betschallenge.data.network.source

import android.util.Log
import com.example.betschallenge.data.entity.UserResponse
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface IUserSource {
    suspend fun getUser(correo: String, password: String): Either<Failure, UserResponse>
    suspend fun createUser(correo: String, password: String): Either<Failure, UserResponse>
    class IUserSourceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : IUserSource {

        // Función suspendida para obtener un usuario específico basado en el correo y la contraseña
        override suspend fun getUser(correo: String, password: String): Either<Failure, UserResponse> {
            return try {
                // Iniciar sesión con el correo y contraseña usando FirebaseAuth y await()
                val authResult = firebaseAuth.signInWithEmailAndPassword(correo, password).await()

                // Obtener el usuario autenticado desde authResult
                val firebaseUser = authResult.user

                // Validar que el usuario no sea nulo
                if (firebaseUser != null) {
                    // Crear un objeto UserResponse a partir de los valores de FirebaseUser
                    val userResponse = createUserResponse(firebaseUser, password)
                    Either.Right(userResponse)
                } else {
                    Either.Left(Failure.ServiceUncaughtFailure("No se pudo obtener la información del usuario"))
                }
            } catch (e: Exception) {
                // Retornar un error si ocurre un problema durante la autenticación
                Either.Left(Failure.ServiceUncaughtFailure(e.message.toString()))
            }
        }

        override suspend fun createUser(
            correo: String,
            password: String
        ): Either<Failure, UserResponse> {
            return try {
                val authResult = firebaseAuth.createUserWithEmailAndPassword(correo, password).await()
                val user = authResult.user
                if (user != null) {
                    // Crear un objeto UserResponse a partir de los valores de FirebaseUser
                    val userResponse = createUserResponse(user, password)
                    Log.d("createUser", "createUser: $userResponse")
                    Either.Right(userResponse)
                }else {
                        Either.Left(Failure.ServiceUncaughtFailure("No se pudo Crear el usuario"))
                    }
            } catch (e: Exception) {
                Either.Left(Failure.ServiceUncaughtFailure(e.message.toString()))
            }
        }

        // Crear el objeto UserResponse basado en el FirebaseUser
        private fun createUserResponse(firebaseUser: FirebaseUser, password: String): UserResponse {
            return UserResponse(
                name =   firebaseUser.email?.substringBefore('.')?.substringBefore('@')?: "Unknown", // Usar el nombre del usuario o "Unknown"
                correo = firebaseUser.email ?: "No Email",    // Usar el correo o un valor por defecto
            )
        }
    }
}