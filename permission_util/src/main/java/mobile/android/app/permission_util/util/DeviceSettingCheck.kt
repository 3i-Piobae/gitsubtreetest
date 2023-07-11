package mobile.android.app.permission_util.util

import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import mobile.android.app.permission_util.PermissionUtilContent

object DeviceSettingCheck {

    private val context
        get() = PermissionUtilContent.context

    fun isLocationGPSEnable() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            context.getSystemService(LocationManager::class.java).isLocationEnabled
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // This is Deprecated in API 28
            Settings.Secure.getInt(
                context.contentResolver, Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            ) != Settings.Secure.LOCATION_MODE_OFF
        } else true

    fun hasBLEService(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            (context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        } else {
            false
        }

    fun isBLEEnable(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(BluetoothManager::class.java)
                ?.adapter
                ?.isEnabled
                ?: false
        } else {
            true
        }
}