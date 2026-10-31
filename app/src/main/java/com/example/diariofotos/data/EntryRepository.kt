package com.example.diariofotos.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/** Persistência local simples: um arquivo JSON em filesDir (fotos ficam em filesDir/photos). */
class EntryRepository(context: Context) {
    private val file = File(context.filesDir, "entries.json")
    val photosDir = File(context.filesDir, "photos").apply { mkdirs() }

    fun load(): List<DiaryEntry> {
        if (!file.exists()) return emptyList()
        return runCatching {
            val arr = JSONArray(file.readText())
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                DiaryEntry(
                    id = o.getString("id"),
                    photoPath = o.getString("photoPath"),
                    description = o.getString("description"),
                    latitude = if (o.isNull("lat")) null else o.getDouble("lat"),
                    longitude = if (o.isNull("lng")) null else o.getDouble("lng"),
                    createdAt = o.getLong("createdAt")
                )
            }
        }.getOrDefault(emptyList())
    }

    fun save(entries: List<DiaryEntry>) {
        val arr = JSONArray()
        entries.forEach {
            arr.put(JSONObject().apply {
                put("id", it.id)
                put("photoPath", it.photoPath)
                put("description", it.description)
                put("lat", it.latitude ?: JSONObject.NULL)
                put("lng", it.longitude ?: JSONObject.NULL)
                put("createdAt", it.createdAt)
            })
        }
        file.writeText(arr.toString())
    }
}
