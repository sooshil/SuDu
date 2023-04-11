package com.sukajee.sudu.data.local

import androidx.room.*
import com.sukajee.sudu.data.model.Sudu

@Dao
interface SuduDao {

    @Query("SELECT * FROM sudus")
    suspend fun getAllSudus(): List<Sudu>

    @Query("SELECT * FROM sudus WHERE id = :suduId")
    suspend fun getSudu(suduId: Int): Sudu

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSudu(sudu: Sudu)

    @Delete
    suspend fun deleteSudu(sudu: Sudu)

    @Query("DELETE FROM sudus WHERE isCompleted = true")
    suspend fun deleteCompleted()
}
