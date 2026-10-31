package com.example.diariofotos.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.diariofotos.data.DiaryEntry
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(entry: DiaryEntry?, onDelete: () -> Unit, onBack: () -> Unit) {
    val ctx = LocalContext.current
    var confirm by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Entrada") },
            navigationIcon = { IconButton(onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar") } },
            actions = { if (entry != null) IconButton({ confirm = true }) { Icon(Icons.Default.Delete, "Excluir") } }
        )
    }) { pad ->
        if (entry == null) { Box(Modifier.padding(pad)) { Text("Entrada não encontrada") }; return@Scaffold }
        Column(Modifier.padding(pad).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(File(entry.photoPath), null, Modifier.fillMaxWidth().height(320.dp), contentScale = ContentScale.Crop)
            Text(formatDate(entry.createdAt), style = MaterialTheme.typography.labelMedium)
            Text(entry.description.ifBlank { "(sem descrição)" }, style = MaterialTheme.typography.bodyLarge)
            if (entry.latitude != null && entry.longitude != null) {
                Text("📍 %.5f, %.5f".format(entry.latitude, entry.longitude))
                Button(onClick = {
                    ctx.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:${entry.latitude},${entry.longitude}?q=${entry.latitude},${entry.longitude}")))
                }) { Text("Abrir no mapa") }
            }
        }
    }
    if (confirm) AlertDialog(
        onDismissRequest = { confirm = false },
        title = { Text("Excluir entrada?") },
        text = { Text("A foto e os dados serão removidos.") },
        confirmButton = { TextButton({ confirm = false; onDelete() }) { Text("Excluir") } },
        dismissButton = { TextButton({ confirm = false }) { Text("Cancelar") } }
    )
}
