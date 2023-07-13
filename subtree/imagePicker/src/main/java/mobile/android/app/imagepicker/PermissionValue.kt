package mobile.android.app.imagepicker

import android.Manifest
import android.os.Build

internal object PermissionValue {

    internal val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    internal val storagePermission =
        if (Build.VERSION.SDK_INT <= 32) {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else emptyArray()

}