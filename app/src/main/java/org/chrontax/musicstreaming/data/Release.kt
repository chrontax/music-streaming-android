package org.chrontax.musicstreaming.data

import kotlin.uuid.Uuid

enum class ReleaseType {
    EP,
    Album,
    Single,
    Compilation,
}

data class Release(
    val id: Uuid,
    val name: String,
    val original_name: String,
    val alt_names: List<String>,
    val type: ReleaseType,
    val artists: List<Uuid>,
    val tracks: List<Track>,
)

data class NewRelease(
    val name: String,
    val original_name: String,
    val alt_names: List<String>,
    val type: ReleaseType,
    val artists: List<Uuid>,
    val tracks: List<NewTrack>,
)