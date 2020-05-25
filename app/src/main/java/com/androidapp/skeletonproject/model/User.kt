package com.androidapp.skeletonproject.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index("user_id")])

data class User(
    @PrimaryKey(autoGenerate = false)
    var user_id: String,
    var profile_image: String,
    var name: String,
    var email: String
)


