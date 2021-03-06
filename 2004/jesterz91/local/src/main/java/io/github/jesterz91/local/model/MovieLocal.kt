package io.github.jesterz91.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
internal data class MovieLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "actor")
    val actor: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "link")
    val link: String,
    @ColumnInfo(name = "pubDate")
    val pubDate: String,
    @ColumnInfo(name = "userRating")
    val userRating: Float,
    @ColumnInfo(name = "searchQuery")
    val searchQuery: String = ""
)