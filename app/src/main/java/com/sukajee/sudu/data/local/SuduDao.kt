package com.sukajee.sudu.data.local

import androidx.room.*
import com.sukajee.sudu.data.model.Sudu
import kotlinx.coroutines.flow.Flow

@Dao
interface SuduDao {

    @Query("SELECT * FROM sudus")
    fun getAllSudus(): Flow<List<Sudu>>

    @Query("SELECT * FROM sudus WHERE id = :suduId")
    suspend fun getSudu(suduId: Int): Sudu

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSudu(sudu: Sudu)

    @Delete
    suspend fun deleteSudu(sudu: Sudu)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSudu(sudu: Sudu)

    @Query("DELETE FROM sudus WHERE isCompleted = 1")
    suspend fun deleteCompleted()
}
