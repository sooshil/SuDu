package com.sukajee.sudu.di

import android.content.Context
import androidx.room.Room
import com.sukajee.sudu.data.local.SuduDao
import com.sukajee.sudu.data.local.SuduDatabase
import com.sukajee.sudu.data.repository.BaseRepository
import com.sukajee.sudu.data.repository.SuduRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): SuduDatabase =
        Room.databaseBuilder(
            context,
            SuduDatabase::class.java,
            "sudu-database"
        ).build()

    @Provides
    @Singleton
    fun provideDao(database: SuduDatabase): SuduDao = database.dao()

    @Provides
    @Singleton
    fun provideRepository(dao: SuduDao): BaseRepository = SuduRepository(dao)

}
