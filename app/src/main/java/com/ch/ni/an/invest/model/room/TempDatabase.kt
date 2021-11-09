package com.ch.ni.an.invest.roomAnimeChar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.lang.IllegalStateException


@Entity(tableName = "tempDB")
data class TitleAnime(
    @PrimaryKey(autoGenerate = false) val title: String
    )


@Dao
interface TempDao{

    @Query("SELECT * FROM tempDB")
    fun getAll(): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTitle(titleAnime :TitleAnime)
}

@Database(entities = [TitleAnime::class], version = 1, exportSchema = false)
abstract class TempDatabase: RoomDatabase(){
    abstract fun tempDao(): TempDao

    companion object {
        @Volatile
        private var INSTANCE : TempDatabase? = null

        fun getDatabase(context :Context): TempDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    TempDatabase::class.java,
                    "tempDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

        fun get(): TempDatabase{
            return INSTANCE ?:
            throw IllegalStateException("DB Must be init")
        }

    }
}