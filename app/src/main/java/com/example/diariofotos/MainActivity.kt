package com.example.diariofotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import android.net.Uri
import com.example.diariofotos.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { DiaryApp() } }
    }
}

object Routes {
    const val LIST = "list"
    const val CAMERA = "camera"
    const val NEW = "new/{path}"
    const val DETAIL = "detail/{id}"
    fun new(path: String) = "new/${Uri.encode(path)}"
    fun detail(id: String) = "detail/$id"
}

@androidx.compose.runtime.Composable
fun DiaryApp(vm: DiaryViewModel = viewModel()) {
    val nav = rememberNavController()
    val entries by vm.entries.collectAsState()

    NavHost(nav, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            ListScreen(
                entries = entries,
                onAdd = { nav.navigate(Routes.CAMERA) },
                onOpen = { nav.navigate(Routes.detail(it)) }
            )
        }
        composable(Routes.CAMERA) {
            CameraScreen(
                newFile = vm::newPhotoFile,
                onCaptured = { path ->
                    nav.navigate(Routes.new(path)) { popUpTo(Routes.CAMERA) { inclusive = true } }
                },
                onBack = { nav.popBackStack() }
            )
        }
        composable(Routes.NEW, arguments = listOf(navArgument("path") { type = NavType.StringType })) {
            val path = it.arguments?.getString("path").orEmpty()
            NewEntryScreen(
                photoPath = path,
                onSave = { desc, lat, lng ->
                    vm.addEntry(path, desc, lat, lng)
                    nav.popBackStack(Routes.LIST, inclusive = false)
                },
                onCancel = { vm.discardPhoto(path); nav.popBackStack(Routes.LIST, inclusive = false) }
            )
        }
        composable(Routes.DETAIL) {
            val id = it.arguments?.getString("id").orEmpty()
            DetailScreen(
                entry = entries.firstOrNull { e -> e.id == id },
                onDelete = { vm.deleteEntry(id); nav.popBackStack() },
                onBack = { nav.popBackStack() }
            )
        }
    }
}
