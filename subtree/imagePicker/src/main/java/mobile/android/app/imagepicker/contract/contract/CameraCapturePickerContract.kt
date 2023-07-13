package mobile.android.app.imagepicker.contract.contract

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import mobile.android.app.imagepicker.ImagePickerContent
import mobile.android.app.imagepicker.contract.data.ImagePickerResult
import mobile.android.app.imagepicker.toFilePath
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraCapturePickerContract : ActivityResultContract<Unit, ImagePickerResult>() {

    var uri: Uri? = null

    override fun createIntent(context: Context, input: Unit): Intent {
        val cameraPictureUrl = createImageUri(context)
        uri = cameraPictureUrl
        val pictureChooseIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        pictureChooseIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPictureUrl)
        grantWritePermission(context, pictureChooseIntent, cameraPictureUrl!!)
        return pictureChooseIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ImagePickerResult {
        val uri = uri
        return if (uri?.toFilePath(ImagePickerContent.context)
                ?.run(::File)
                ?.exists() == true
        ) {
            ImagePickerResult.ImageData(uri)
        } else {
            ImagePickerResult.EmptyImageData
        }
    }

    private fun createImageUri(context: Context): Uri? {
        val contentResolver = context.contentResolver
        val cv = ContentValues()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        cv.put(MediaStore.Images.Media.TITLE, "$timeStamp.jpg")
        cv.put(MediaStore.Images.Media.DISPLAY_NAME, "$timeStamp.jpg")
        cv.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
        }
//        cv.put(MediaStore.Images.Media.DISPLAY_NAME, timeStamp)

        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun grantWritePermission(context: Context, intent: Intent, uri: Uri) {
        val resInfoList =
            context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(
                packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }


}
