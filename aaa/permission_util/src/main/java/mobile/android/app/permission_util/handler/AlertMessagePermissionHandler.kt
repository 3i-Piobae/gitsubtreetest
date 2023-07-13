package mobile.android.app.permission_util.handler

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.ContextThemeWrapper
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.CallSuper
import mobile.android.app.permission_util.data.PermissionRequest
import mobile.android.app.permission_util.data.PermissionResponse
import mobile.android.app.permission_util.option.DialogOption
import mobile.android.app.permission_util.util.permissionCheckToGroup
import mobile.android.app.permission_util.util.toPermissionResponse

open class AlertMessagePermissionHandler : PermissionHandlerImpl(){

    internal lateinit var permissionSettingPageContract: ActivityResultLauncher<PermissionRequest>

    internal lateinit var getContextFunction: () -> Context

    private val context get() = getContextFunction()

    private fun launchToSettingPage(request: PermissionRequest) =
        permissionSettingPageContract.launch(request)

    private fun deny(request: PermissionRequest) =
        onPermissionSettingPageDenied(request.permissionCheckToGroup().toPermissionResponse())

    @CallSuper
    override fun onDenied(response: PermissionResponse) =
        showPermissionWithSettingPageDialog(PermissionRequest(response.requestPermissions))

    @CallSuper
    override fun onBlocked(response: PermissionResponse) =
        showPermissionWithSettingPageDialog(PermissionRequest(response.requestPermissions))

    private fun showPermissionWithSettingPageDialog(request: PermissionRequest) =
        buildDialog(bindPermissionDialog(DialogOption.Builder(request)), context, request).show()

    open fun bindPermissionDialog(builder: DialogOption.Builder): DialogOption = builder.build()

    open fun buildDialog(
        permissionAlertDialog: DialogOption,
        context: Context,
        request: PermissionRequest
    ): Dialog =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            AlertDialog.Builder(context, permissionAlertDialog.dialogTheme)
        } else {
            AlertDialog.Builder(ContextThemeWrapper(context, permissionAlertDialog.dialogTheme))
        }
            .setTitle(permissionAlertDialog.title)
            .setMessage(permissionAlertDialog.message)
            .setPositiveButton(permissionAlertDialog.positiveButton) { _, _ ->
                launchToSettingPage(request)
            }
            .setNegativeButton(permissionAlertDialog.negativeButton) { _, _ ->
                deny(request)
            }
            .setOnCancelListener { deny(request) }
            .create()


}