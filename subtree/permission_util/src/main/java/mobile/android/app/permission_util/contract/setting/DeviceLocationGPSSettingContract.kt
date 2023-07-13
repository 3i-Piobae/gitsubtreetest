package mobile.android.app.permission_util.contract.setting

import android.content.Context
import android.provider.Settings
import mobile.android.app.permission_util.util.DeviceSettingCheck

class DeviceLocationGPSSettingContract :
    DeviceSettingContract(Settings.ACTION_LOCATION_SOURCE_SETTINGS) {

    override fun settingEnableCheck(context: Context): Boolean =
        DeviceSettingCheck.isLocationGPSEnable()
}