package com.example.betschallenge.data.network.source


import com.example.betschallenge.data.entity.BetsResponseItem
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

interface IBetsSource {

    suspend fun getBets(): Either<Failure, List<BetsResponseItem>>

    class IBetsSourceImpl @Inject constructor( database: FirebaseDatabase) : IBetsSource {
        private val databaseReference: DatabaseReference = database.getReference("betsHistory")

        override suspend fun getBets(): Either<Failure, List<BetsResponseItem>> {
            return try {
                val bets = getBetsFromDatabase()
                Either.Right(bets)
            } catch (e: Exception) {
                Either.Left(Failure.ServiceUncaughtFailure(e.message.toString()))
            }
        }

        private suspend fun getBetsFromDatabase(): List<BetsResponseItem> =
            suspendCoroutine { continuation ->
                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        try {
                            val bets = mutableListOf<BetsResponseItem>()
                            for (snapshot in dataSnapshot.children) {
                                val bet = snapshot.getValue(BetsResponseItem::class.java)
                                bet?.let { bets.add(it) }
                            }
                            continuation.resume(bets)
                        } catch (e: Exception) {
                            continuation.resumeWithException(e)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        continuation.resumeWithException(databaseError.toException())
                    }
                })
            }
    }
}