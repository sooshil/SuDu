package com.sukajee.sudu.data.repository

import com.sukajee.sudu.data.local.SuduDao
import com.sukajee.sudu.data.model.Sudu
import javax.inject.Inject

class SuduRepository @Inject constructor(
    private val dao: SuduDao
) : BaseRepository {

    override suspend fun insertSudu(sudu: Sudu) = dao.insertSudu(sudu)
    override suspend fun getSudu(suduId: Int): Sudu = dao.getSudu(suduId)
    override suspend fun getAllSudus(): List<Sudu> = dao.getAllSudus()
    override suspend fun deleteSudu(sudu: Sudu) = dao.deleteSudu(sudu)
    override suspend fun deleteCompletedSudus() = dao.deleteCompleted()
}
