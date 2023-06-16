package com.bangkit.capstone_project.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "location")
    var location: String? = null,
    @ColumnInfo(name = "startDate")
    var startDate: Long,
    @ColumnInfo(name = "frequency")
    var frequency: Int,
    @ColumnInfo(name = "lastScheduledDate")
    var lastScheduledDate: Long? = null,
    @ColumnInfo(name = "nextScheduledDate")
    var nextScheduledDate: Long? = null,
    @ColumnInfo(name = "plantId")
    var plantId: Int = 0,
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
):Parcelable