package com.example.diariofotos.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.diariofotos.data.DiaryEntry
import com.example.diariofotos.data.EntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.util.UUID

/** Fonte única de estado (UDF): a UI observa [entries] e dispara eventos pelos métodos. */
class DiaryViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = EntryRepository(app)

    private val _entries = MutableStateFlow(repo.load().sortedByDescending { it.createdAt })
    val entries: StateFlow<List<DiaryEntry>> = _entries.asStateFlow()

    fun newPhotoFile(): File = File(repo.photosDir, "IMG_${System.currentTimeMillis()}.jpg")

    fun addEntry(photoPath: String, description: String, lat: Double?, lng: Double?) {
        val entry = DiaryEntry(UUID.randomUUID().toString(), photoPath, description, lat, lng, System.currentTimeMillis())
        _entries.update { listOf(entry) + it }
        repo.save(_entries.value)
    }

    fun deleteEntry(id: String) {
        _entries.value.firstOrNull { it.id == id }?.let { File(it.photoPath).delete() }
        _entries.update { list -> list.filterNot { it.id == id } }
        repo.save(_entries.value)
    }

    fun discardPhoto(path: String) { File(path).delete() }
}
