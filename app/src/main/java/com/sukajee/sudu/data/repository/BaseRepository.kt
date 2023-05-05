package com.sukajee.sudu.data.repository

import com.sukajee.sudu.data.model.Sudu
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    fun getAllSudus(): Flow<List<Sudu>>
    suspend fun insertSudu(sudu: Sudu)
    suspend fun getSudu(suduId: Int): Sudu
    suspend fun deleteSudu(sudu: Sudu)
    suspend fun updateSudu(sudu: Sudu)
    suspend fun deleteCompletedSudus()
}
