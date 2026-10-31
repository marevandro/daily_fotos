package com.example.diariofotos.data

data class DiaryEntry(
    val id: String,
    val photoPath: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?,
    val createdAt: Long
)
