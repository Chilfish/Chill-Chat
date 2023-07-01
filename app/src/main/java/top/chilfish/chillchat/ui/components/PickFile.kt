package top.chilfish.chillchat.ui.components

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.io.File

/**
 * 从文件列表选择图片
 */
@Composable
fun PickFile(
    navController: NavController = rememberNavController(),
    type: String = "image/*",
    pickedFile: (File) -> Unit,
    onPicked: @Composable (() -> Unit) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val contentResolver = navController.context.contentResolver
            val cacheDir = navController.context.cacheDir

            val inputStream = contentResolver.openInputStream(uri)
            val temp = File.createTempFile("picked_", null, cacheDir)

            temp.outputStream()
                .use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
            val file = File(
                cacheDir,
                getRealPathFromURI(contentResolver, uri)
            )
            temp.renameTo(file)
            pickedFile(file)
            inputStream?.close()
        }
    }

    onPicked { launcher.launch(type) }
}

private fun getRealPathFromURI(contentResolver: ContentResolver, uri: Uri): String {
    val cursor = contentResolver.query(uri, null, null, null, null)
    val index = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: return ""
    cursor.moveToFirst()
    val fileName = cursor.getString(index)
    cursor.close()
    return fileName
}