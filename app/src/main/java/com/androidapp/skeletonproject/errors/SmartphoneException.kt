package quantum.airbus.com.mobile.disruption.errors

data class SmartphoneException(
    val statusCode: Int = 999,
    val technicalMessage: String?,
    override val message: String = "Internal Error"
) : Exception()
