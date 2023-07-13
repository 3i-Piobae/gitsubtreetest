package mobile.android.app.permission_util.contract.setting

import android.bluetooth.BluetoothAdapter
import android.content.Context
import mobile.android.app.permission_util.util.DeviceSettingCheck

class DeviceBLESettingContract : DeviceSettingContract(BluetoothAdapter.ACTION_REQUEST_ENABLE) {

    override fun settingEnableCheck(context: Context): Boolean =
        DeviceSettingCheck.isBLEEnable()

}