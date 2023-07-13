package mobile.android.app.permission_util.util

import android.content.pm.PackageManager
import android.os.Build
import mobile.android.app.permission_util.PermissionUtilContent
import mobile.android.app.permission_util.data.PermissionRequest
import mobile.android.app.permission_util.data.PermissionResponse


internal fun PermissionRequest.toEmptyDinedResponse() =
    PermissionResponse(requestPermissions, emptyList())

internal fun Map<Boolean, List<String>>.toPermissionResponse() =
    PermissionResponse(
        (get(true) ?: emptyList()) + (get(false) ?: emptyList()),
        get(false)?: emptyList()
    )

internal fun PermissionRequest.permissionCheckToGroup() =
    requestPermissions.permissionCheckToGroup()

internal fun List<String>.permissionCheckToGroup(): Map<Boolean, List<String>> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        groupBy { PermissionUtilContent.context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }
    } else {
        mapOf(true to this)
    }