package com.example.watchmodeapp.data.models

data class TitleDetails(
    val critic_score: Int?=0,
    val end_year: Int?=0,
    val genre_names: List<String>? = emptyList(),
    val plot_overview: String? ="",
    val posterLarge: String?="",
    val title: String? ="",
    val type: String? = "",
    val us_rating: String? ="",
    val user_rating: Double?= 0.0,
    val year: Int?=0
)