package mobile.android.app.permission_util.contract

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import mobile.android.app.permission_util.data.PermissionRequest

internal class PermissionSettingPageContract :
    ActivityResultContract<PermissionRequest, PermissionRequest>() {

    private var permissionRequest = PermissionRequest(emptyList())

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    override fun createIntent(context: Context, input: PermissionRequest): Intent {
        permissionRequest = input
        return Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?) = permissionRequest

}
