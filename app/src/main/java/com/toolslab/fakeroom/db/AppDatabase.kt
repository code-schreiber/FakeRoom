package com.toolslab.fakeroom.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        Apple::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appleDao(): AppleDao

}
