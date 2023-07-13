package mobile.android.app.permission_util.handler

import mobile.android.app.permission_util.data.PermissionResponse

abstract class PermissionHandlerImpl : PermissionHandler() {

    final override fun onCallback(callbackState: PermissionCallbackState) {
        stateCallback(callbackState)
        when (callbackState) {
            is PermissionCallbackState.Granted ->
                onGranted(callbackState.response)

            is PermissionCallbackState.Denied ->
                onDenied(callbackState.response)

            is PermissionCallbackState.Blocked ->
                onBlocked(callbackState.response)

            is PermissionCallbackState.PermissionSettingPageResponse ->
                onPermissionSettingPageResponse(callbackState.response)

            is PermissionCallbackState.PermissionSettingPageDenied ->
                onPermissionSettingPageDenied(callbackState.response)
        }
    }

    open fun stateCallback(stateCallback: PermissionCallbackState) {}

    open fun onGranted(response: PermissionResponse) {}

    open fun onDenied(response: PermissionResponse) {}

    open fun onBlocked(response: PermissionResponse) {}

    open fun onPermissionSettingPageResponse(response: PermissionResponse) {}

    open fun onPermissionSettingPageDenied(response: PermissionResponse) {}

}