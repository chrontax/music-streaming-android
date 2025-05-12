package org.chrontax.musicstreaming.network.moshi_adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlin.uuid.Uuid

class UuidAdapter {
    @ToJson
    fun toJson(uuid: Uuid): String = uuid.toString()

    @FromJson
    fun fromJson(uuidString: String): Uuid = Uuid.parse(uuidString)

}