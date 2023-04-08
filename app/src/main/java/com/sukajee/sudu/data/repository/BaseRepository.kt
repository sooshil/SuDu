package com.sukajee.sudu.data.repository

import com.sukajee.sudu.data.model.Sudu

interface BaseRepository {

    suspend fun insertSudu(sudu: Sudu)
    suspend fun getSudu(suduId: Int): Sudu
    suspend fun getAllSudus(): List<Sudu>
    suspend fun deleteSudu(sudu: Sudu)
    suspend fun deleteCompletedSudus()
}
