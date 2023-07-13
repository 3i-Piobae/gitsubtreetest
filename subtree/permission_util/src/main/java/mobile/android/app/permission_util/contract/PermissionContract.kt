package mobile.android.app.permission_util.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import mobile.android.app.permission_util.data.PermissionRequest
import mobile.android.app.permission_util.data.PermissionResponse

internal class PermissionContract :
    ActivityResultContract<PermissionRequest, PermissionResponse>() {
    override fun createIntent(context: Context, input: PermissionRequest): Intent =
        Intent(ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS)
            .putExtra(
                ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS,
                input.requestPermissions.toTypedArray()
            )

    override fun parseResult(resultCode: Int, intent: Intent?): PermissionResponse {
        if (resultCode != Activity.RESULT_OK) return PermissionResponse.empty()
        if (intent == null) return PermissionResponse.empty()

        val permissions =
            intent.getStringArrayExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS)
        val grantResults =
            intent.getIntArrayExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS)
        if (grantResults == null || permissions == null) return PermissionResponse.empty()

        val grantState = grantResults.map { result ->
            result == PackageManager.PERMISSION_GRANTED
        }
        val permissionMapResult = permissions.filterNotNull().zip(grantState).toMap()

        val requestPermissions: List<String> =
            permissionMapResult.keys.toList()
        val deniedPermissions: List<String> =
            permissionMapResult.filterNot(Map.Entry<String, Boolean>::value).keys.toList()

        return PermissionResponse(requestPermissions, deniedPermissions)
    }

}
