package mobile.android.app.imagepicker.contract.contract

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.net.toUri
import mobile.android.app.imagepicker.ImagePickerContent
import mobile.android.app.imagepicker.contract.data.ImagePickerResult
import mobile.android.app.imagepicker.toFilePath

class GalleryImagePickerContract : ActivityResultContract<Unit, ImagePickerResult>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        val pictureChooseIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            pictureChooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }
        return pictureChooseIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ImagePickerResult =
        intent?.data
            ?.run {
                ImagePickerResult.ImageData(
                    toFilePath(ImagePickerContent.context)?.toUri() ?: this
                )
            }
            ?: ImagePickerResult.EmptyImageData
}