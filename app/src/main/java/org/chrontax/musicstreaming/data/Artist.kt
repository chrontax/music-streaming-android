package org.chrontax.musicstreaming.data

import kotlin.uuid.Uuid

data class Artist(
    val id: Uuid,
    val name: String,
    val original_name: String,
    val alt_names: List<String>,
)

data class NewArtist(
    val name: String,
    val original_name: String,
    val alt_names: List<String>,
)