package mobile.android.app.permission_util

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import mobile.android.app.permission_util.contract.PermissionContract
import mobile.android.app.permission_util.contract.PermissionSettingPageContract
import mobile.android.app.permission_util.data.PermissionRequest
import mobile.android.app.permission_util.data.PermissionResponse
import mobile.android.app.permission_util.handler.AlertMessagePermissionHandler
import mobile.android.app.permission_util.handler.PermissionCallbackState
import mobile.android.app.permission_util.handler.PermissionHandler
import mobile.android.app.permission_util.util.permissionCheckToGroup
import mobile.android.app.permission_util.util.toPermissionResponse


@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
fun Fragment.registerPermission(
    handler: PermissionHandler,
    bind: (PermissionCallbackState) -> Unit = {}
): PermissionRequestLauncher {
    val register = registerPermission {
        handler.callback(it)
        bind(it)
    }

    if (handler is AlertMessagePermissionHandler) {
        handler.permissionSettingPageContract = register.settingPageLauncher
        handler.getContextFunction = { requireContext() }
    }
    return register
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
fun Fragment.registerPermission(bind: (PermissionCallbackState) -> Unit): PermissionRequestLauncher {
    return registerPermissionBind(
        registerForActivityResult(
            PermissionContract(),
            registerPermissionHandler(::shouldShowRequestPermissionRationale, bind)
        ),
        registerForActivityResult(
            PermissionSettingPageContract(),
            registerPermissionSettingPageHandler(bind)
        ),
        bind,
    )
}

@RequiresApi(Build.VERSION_CODES.M)
fun ComponentActivity.registerPermission(
    handler: PermissionHandler,
    bind: (PermissionCallbackState) -> Unit = {}
): PermissionRequestLauncher {
    val register = registerPermission {
        handler.callback(it)
        bind(it)
    }
    if (handler is AlertMessagePermissionHandler) {
        handler.permissionSettingPageContract = register.settingPageLauncher
        handler.getContextFunction = { this }
    }
    return register
}


@RequiresApi(Build.VERSION_CODES.M)
fun ComponentActivity.registerPermission(callback: (PermissionCallbackState) -> Unit): PermissionRequestLauncher {
    return registerPermissionBind(
        registerForActivityResult(
            PermissionContract(),
            registerPermissionHandler(::shouldShowRequestPermissionRationale, callback)
        ),
        registerForActivityResult(
            PermissionSettingPageContract(),
            registerPermissionSettingPageHandler(callback)
        ),
        callback
    )
}

private fun registerPermissionBind(
    permissionLauncher: ActivityResultLauncher<PermissionRequest>,
    settingPageLauncher: ActivityResultLauncher<PermissionRequest>,
    callback: (PermissionCallbackState) -> Unit
): PermissionRequestLauncher {
    return PermissionRequestLauncher(
        permissionLauncher,
        settingPageLauncher,
        callback
    )
}

private fun registerPermissionSettingPageHandler(callback: (PermissionCallbackState) -> Unit): (PermissionRequest) -> Unit =
    { request ->
        val response = request.permissionCheckToGroup().toPermissionResponse()
        if (response.deniedPermissions.isEmpty()) {
            callback(PermissionCallbackState.PermissionSettingPageResponse(response))
        } else {
            callback(PermissionCallbackState.PermissionSettingPageDenied(response))
        }
    }

private fun registerPermissionHandler(
    shouldShowRequestPermissionRationale: (String) -> Boolean,
    callback: (PermissionCallbackState) -> Unit
): (PermissionResponse) -> Unit =
    { response ->
        if (response.requestPermissions.isNotEmpty()) {
            if (response.deniedPermissions.isEmpty()) {
                callback(PermissionCallbackState.Granted(response))
            } else {
                if (
                    response.deniedPermissions
                        .groupBy(shouldShowRequestPermissionRationale)
                        .containsKey(true)
                ) {
                    callback(PermissionCallbackState.Denied(response))
                } else {
                    callback(PermissionCallbackState.Blocked(response))
                }
            }

        }

    }