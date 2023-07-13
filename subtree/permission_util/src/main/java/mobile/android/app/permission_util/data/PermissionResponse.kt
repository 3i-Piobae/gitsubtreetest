package mobile.android.app.permission_util.data

data class PermissionResponse internal constructor(
    val requestPermissions: List<String>,
    val deniedPermissions: List<String>
){
    companion object{
        fun empty() = PermissionResponse(emptyList(), emptyList())
    }
}