package org.chrontax.musicstreaming.data

import kotlin.uuid.Uuid

enum class Quality {
    None,
    Low,
    Medium,
    High,
    Highest
}

data class Track(
    val id: Uuid,
    val disc_number: Int,
    val track_number: Int,
    val name: String,
    val original_name: String,
    val alt_names: List<String>,
    val quality: Quality,
    val release_id: Uuid,
    val artists: List<Uuid>,
)

data class NewTrack(
    val disc_number: Int,
    val track_number: Int,
    val name: String,
    val original_name: String,
    val alt_names: List<String>,
    val quality: Quality,
    val release_id: Uuid,
    val artists: List<Uuid>,
)
