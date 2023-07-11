package mobile.android.app.permission_util.option

import android.os.Build
import androidx.annotation.StringRes
import mobile.android.app.permission_util.PermissionUtilContent
import mobile.android.app.permission_util.data.PermissionRequest

class DialogOption private constructor(val builder: Builder) {

    val request: PermissionRequest = builder.request
    val title: String = builder.title
    val message: String = builder.message
    val positiveButton: String = builder.positiveButton
    val negativeButton: String = builder.negativeButton

    val dialogTheme : Int = builder.dialogTheme

    class Builder internal constructor(
        internal val request: PermissionRequest
    ) {
        val context by lazy { PermissionUtilContent.context }

        companion object {
            private const val DEFAULT_SETTINGS_TEXT = "Settings"
            private const val DEFAULT_SETTINGS_DIALOG_TITLE = "Permissions Required"
            private const val DEFAULT_SETTINGS_DIALOG_MESSAGE =
                "Required permission(s) have been set not to ask again! Please provide them from settings."
        }

        internal var title: String = DEFAULT_SETTINGS_DIALOG_TITLE
        internal var message: String = DEFAULT_SETTINGS_DIALOG_MESSAGE
        internal var positiveButton: String = DEFAULT_SETTINGS_TEXT
        internal var negativeButton: String = context.getString(android.R.string.cancel)
        internal var dialogTheme: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.R.style.Theme_Material_Light_Dialog
        } else {
            android.R.style.Theme_Dialog
        }

        fun setTitle(@StringRes res: Int): Builder {
            title = context.getString(res)
            return this
        }

        fun setTitle(text: String): Builder {
            title = text
            return this
        }

        fun setMessage(@StringRes res: Int): Builder {
            message = context.getString(res)
            return this
        }

        fun setMessage(text: String): Builder {
            message = text
            return this
        }

        fun setPositiveButton(@StringRes res: Int): Builder {
            positiveButton = context.getString(res)
            return this
        }

        fun setPositiveButton(text: String): Builder {
            positiveButton = text
            return this
        }

        fun setNegativeButton(@StringRes res: Int): Builder {
            negativeButton = context.getString(res)
            return this
        }

        fun setNegativeButton(text: String): Builder {
            negativeButton = text
            return this
        }

        fun setTheme(themeRes: Int) {
            dialogTheme = themeRes
        }

        fun build() = DialogOption(this)

    }

}