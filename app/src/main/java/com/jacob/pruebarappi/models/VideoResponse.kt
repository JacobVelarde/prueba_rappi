package com.jacob.pruebarappi.models

data class VideoResponse (
    val id: Long,
    val results: List<Video>
)

data class Video (
    val iso639_1: String,
    val iso3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Long,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
    val id: String
)