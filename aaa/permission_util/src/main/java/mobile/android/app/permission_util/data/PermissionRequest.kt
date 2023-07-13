package mobile.android.app.permission_util.data

data class PermissionRequest constructor(
    val requestPermissions: List<String>
) {
    constructor(vararg item: String) : this(listOf(*item))
}