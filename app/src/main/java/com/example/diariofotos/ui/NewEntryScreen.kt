package com.example.diariofotos.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun NewEntryScreen(photoPath: String, onSave: (String, Double?, Double?) -> Unit, onCancel: () -> Unit) {
    val ctx = LocalContext.current
    var description by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf<Double?>(null) }
    var lng by remember { mutableStateOf<Double?>(null) }
    var loading by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("Localização não obtida") }

    fun fetchLocation() {
        loading = true
        LocationServices.getFusedLocationProviderClient(ctx)
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { loc ->
                loading = false
                if (loc != null) { lat = loc.latitude; lng = loc.longitude; status = "Localização obtida" }
                else status = "GPS indisponível. Verifique se a localização está ativada."
            }
            .addOnFailureListener { loading = false; status = "Falha ao obter localização" }
    }

    val permLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { r ->
        if (r.values.any { it }) fetchLocation() else status = "Permissão de localização negada"
    }
    fun requestLocation() {
        val ok = ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (ok) fetchLocation()
        else permLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }
    LaunchedEffect(Unit) { requestLocation() }

    Scaffold(topBar = { TopAppBar(title = { Text("Nova entrada") }) }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(File(photoPath), null, Modifier.fillMaxWidth().height(280.dp), contentScale = ContentScale.Crop)
            OutlinedTextField(
                value = description, onValueChange = { description = it },
                label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth(), minLines = 3
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                OutlinedButton(onClick = { requestLocation() }, enabled = !loading) { Text("Atualizar GPS") }
                if (loading) CircularProgressIndicator(Modifier.size(24.dp), strokeWidth = 2.dp)
            }
            Text(if (lat != null) "📍 %.5f, %.5f".format(lat, lng) else status)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onCancel) { Text("Descartar") }
                Button(onClick = { onSave(description.trim(), lat, lng) }) { Text("Salvar") }
            }
        }
    }
}
