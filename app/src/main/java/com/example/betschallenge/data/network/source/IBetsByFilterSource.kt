package com.example.betschallenge.data.network.source

import com.example.betschallenge.data.entity.BetsDetailResponse
import com.example.domain.entity.Either
import com.example.domain.entity.Failure
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface IBetsByFilterSource {
    suspend fun getBetsByFilter(filter: String): Either<Failure,List<BetsDetailResponse>>

    class IBetsByFilterSourceImpl@Inject constructor(database: FirebaseDatabase) : IBetsByFilterSource {
        private val databaseReference: DatabaseReference = database.getReference("betsDetailHistory")
        override suspend fun getBetsByFilter(filter: String):  Either<Failure,List<BetsDetailResponse>> {
            return try {
                // Llamar a la función suspendida para obtener los datos filtrados
                val allBets = getAllBetsFromDatabase()

                val filteredBets = allBets.filter { bet ->
                    bet.betSelections.any { it.eventName.contains(filter, ignoreCase = true) } || // Filtrar por EventName
                            bet.betId.toString() == filter // Filtrar por BetId
                }
                Either.Right(filteredBets)
            } catch (e: Exception) {
                // Retornar una lista vacía en caso de error
                Either.Right(emptyList())
            }
        }
        private suspend fun getAllBetsFromDatabase(): List<BetsDetailResponse> =
            suspendCoroutine { continuation ->
                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            val betsList = mutableListOf<BetsDetailResponse>()
                            for (snapshot in dataSnapshot.children) {
                                val betDetail = snapshot.getValue(BetsDetailResponse::class.java)
                                betDetail?.let { betsList.add(it) }
                            }
                            continuation.resume(betsList)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(error.toException())
                    }
                })
            }
    }
}