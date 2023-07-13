package mobile.android.app.imagepicker

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.database.getStringOrNull
import androidx.fragment.app.Fragment
import mobile.android.app.imagepicker.contract.contract.CameraCapturePickerContract
import mobile.android.app.imagepicker.contract.contract.GalleryImagePickerContract
import mobile.android.app.imagepicker.contract.data.ImagePickerResult

fun Fragment.registerImagePicker(callback: (ImagePickerResult) -> Unit) =
    registerImagePicker(
        registerForActivityResult(CameraCapturePickerContract(), callback),
        registerForActivityResult(GalleryImagePickerContract(), callback)
    )

fun ComponentActivity.registerImagePicker(callback: (ImagePickerResult) -> Unit) =
    registerImagePicker(
        registerForActivityResult(CameraCapturePickerContract(), callback),
        registerForActivityResult(GalleryImagePickerContract(), callback)
    )

private fun registerImagePicker(
    cameraContract: ActivityResultLauncher<Unit>,
    galleryContract: ActivityResultLauncher<Unit>,
) = ImagePickerLauncher(
    cameraContract,
    galleryContract,
)

internal fun Uri.toFilePath(context: Context): String? {
    val defaultFilePath = this.path

    if ( scheme != "content") return defaultFilePath

    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor =
        context.contentResolver.query(this, projection, null, null, null) ?: return defaultFilePath
    val columnIndex =
        cursor.runCatching { getColumnIndexOrThrow(MediaStore.Images.Media.DATA) }
            .getOrNull() ?: return defaultFilePath

    if (!cursor.moveToFirst()) return defaultFilePath
    return cursor.use { it.getStringOrNull(columnIndex) }
}