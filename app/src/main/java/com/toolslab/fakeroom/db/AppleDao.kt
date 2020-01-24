package com.toolslab.fakeroom.db

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface AppleDao {

    @Query("SELECT * FROM apple")
    fun all(): Flowable<List<Apple>>

    @Insert
    fun insert(apple: Apple)

    @Update
    fun update(apple: Apple)

    @Delete
    fun delete(apple: Apple)

    @Query("SELECT * FROM apple WHERE eaten = 0")
    fun findUneaten(): Flowable<List<Apple>>
}
