/*
package org.olu.mvvm.repository.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.marvelousportal.models.Data
import com.marvelousportal.repository.data.Result
import io.reactivex.Single

@Dao
interface ResultDao {

    @Query("SELECT * FROM result")
    fun getUsers(): Single<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(result: Result)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(result: List<Data>?)
}*/
