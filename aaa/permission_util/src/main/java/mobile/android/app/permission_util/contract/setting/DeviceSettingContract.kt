package mobile.android.app.permission_util.contract.setting

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import mobile.android.app.permission_util.PermissionUtilContent

abstract class DeviceSettingContract(private val settingContract : String ) : ActivityResultContract<Unit, Boolean>() {

    override fun createIntent(context: Context, input: Unit): Intent = Intent(settingContract)

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean =
        settingEnableCheck(PermissionUtilContent.context)

    protected abstract fun settingEnableCheck(context: Context) : Boolean

}