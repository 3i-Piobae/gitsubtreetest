package mobile.android.app.imagepicker.contract.data

import android.net.Uri

sealed class ImagePickerResult {

    object EmptyImageData : ImagePickerResult()
    class ImageData(val uri : Uri) : ImagePickerResult()
    class MultiImageData(val uriList : List<Uri>) : ImagePickerResult()

}