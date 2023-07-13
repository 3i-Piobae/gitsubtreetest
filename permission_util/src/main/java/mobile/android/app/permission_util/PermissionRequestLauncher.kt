package mobile.android.app.permission_util

import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import mobile.android.app.permission_util.data.PermissionRequest
import mobile.android.app.permission_util.data.PermissionResponse
import mobile.android.app.permission_util.handler.PermissionCallbackState
import mobile.android.app.permission_util.util.permissionCheckToGroup
import mobile.android.app.permission_util.util.toEmptyDinedResponse

class PermissionRequestLauncher internal constructor(
    private val permissionLauncher: ActivityResultLauncher<PermissionRequest>,
    internal val settingPageLauncher: ActivityResultLauncher<PermissionRequest>,
    private val callback: (PermissionCallbackState) -> Unit
) {

    fun launch(permissions: Array<String>) = launch(permissions.toList())
    fun launch(permissions: List<String>) = launch(PermissionRequest(permissions))

    fun launch(req: PermissionRequest) {
        if (req.requestPermissions.isNotEmpty()) {
            val permissionGroup = req.permissionCheckToGroup()

            val deniedList = permissionGroup[false] ?: emptyList()

            if (deniedList.isNotEmpty()) {
                permissionLauncher.launch(req)
            } else {
                callback(
                    PermissionCallbackState.Granted(
                        PermissionResponse(req.requestPermissions, emptyList())
                    )
                )
            }
        } else {
            callback(PermissionCallbackState.Granted(req.toEmptyDinedResponse()))
        }
    }

    @RequiresApi(23)
    fun launchSettingPage(req: PermissionRequest) {
        settingPageLauncher.launch(req)
    }

}