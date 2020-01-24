package com.toolslab.fakeroom.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apple")
data class Apple(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val eaten: Boolean
)
