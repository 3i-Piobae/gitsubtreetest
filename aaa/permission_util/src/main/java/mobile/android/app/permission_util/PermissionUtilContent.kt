package mobile.android.app.permission_util

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

class PermissionUtilContent : ContentProvider() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Suppress("ObjectPropertyName")
        private var _context: Context? = null

        val context: Context
            get() = _context!!
    }

    override fun onCreate(): Boolean {
        _context = this.context
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        throw java.lang.IllegalStateException()
    }

    override fun getType(uri: Uri): String? {
        throw java.lang.IllegalStateException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw java.lang.IllegalStateException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw java.lang.IllegalStateException()
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw IllegalStateException()
    }
}