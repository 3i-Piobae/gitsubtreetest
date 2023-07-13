package mobile.android.app.permission_util.handler

abstract class PermissionHandler {

    internal val callback: (PermissionCallbackState) -> Unit = { onCallback(it) }

    abstract fun onCallback(callbackState: PermissionCallbackState)

}