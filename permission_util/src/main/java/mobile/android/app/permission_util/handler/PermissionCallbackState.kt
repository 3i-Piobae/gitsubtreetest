package mobile.android.app.permission_util.handler

import mobile.android.app.permission_util.data.PermissionResponse

sealed class PermissionCallbackState(val response: PermissionResponse) {

    /*
    * permission granted
    * */
    class Granted(response: PermissionResponse) : PermissionCallbackState(response)

    /*
    * permission denied
    * */
    class Denied(response: PermissionResponse) : PermissionCallbackState(response)


    /*
    * permission don't ask denied
    * */
    class Blocked(response: PermissionResponse) : PermissionCallbackState(response)


    /*
    * permission setting page
    * granted permission check
    * */
    class PermissionSettingPageResponse(response: PermissionResponse) : PermissionCallbackState(response)


    /*
    * permission setting page
    * denied permission check
    * */
    class PermissionSettingPageDenied(response: PermissionResponse) : PermissionCallbackState(response)

}