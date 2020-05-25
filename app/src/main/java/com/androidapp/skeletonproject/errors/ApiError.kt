package quantum.airbus.com.mobile.disruption.errors

data class ApiError(
    val statusCode: Int,
    val calledService: String,
    val isSnackBar: Boolean = true,
    val technicalMessage: String?,
    override val message: String ="Error while fetching data from network"
) : Exception()

