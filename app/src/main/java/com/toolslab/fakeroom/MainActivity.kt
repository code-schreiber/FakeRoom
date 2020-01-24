package com.toolslab.fakeroom

import android.app.Activity
import android.os.Bundle
import androidx.room.Room
import com.toolslab.fakeroom.db.AppDatabase

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDatabase = Room.databaseBuilder(
            this.applicationContext,
            AppDatabase::class.java,
            "FakeRoomDb"
        ).build()

        val appleDao = appDatabase.appleDao()
        AppleEater(appleDao).eatApples()
    }

}

