package mobile.android.app.imagepicker

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher

class ImagePickerLauncher(
    private val cameraCapture: ActivityResultLauncher<Unit>,
    private val galleryContract: ActivityResultLauncher<Unit>,
) {

    fun cameraCapture() = cameraCapture.launch(Unit)
    fun getSingleImagePicker() = galleryContract.launch(Unit)

    fun getCameraCapturePermission(): Array<String> =
        PermissionValue.cameraPermission + PermissionValue.storagePermission

    fun getSelectPicturePermission(): Array<String> = PermissionValue.storagePermission

    fun checkCameraCapturePermission(): Map<Boolean, List<String>> =
        permissionCheck(ImagePickerContent.context, getCameraCapturePermission())

    fun checkSelectPicturePermission(): Map<Boolean, List<String>> =
        permissionCheck(ImagePickerContent.context, getSelectPicturePermission())

    fun isCameraCapturePermission() = checkCameraCapturePermission()[false] != null

    fun isSelectPicturePermission() = checkSelectPicturePermission()[false] != null


    private fun permissionCheck(
        context: Context,
        permissionArray: Array<String>
    ): Map<Boolean, List<String>> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionArray.groupBy { context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
        } else {
            mapOf(true to getCameraCapturePermission().toList())
        }
    }


}