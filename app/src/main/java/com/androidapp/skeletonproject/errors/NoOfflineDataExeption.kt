package quantum.airbus.com.mobile.disruption.errors

class NoOfflineDataExeption (val statusCode: Int = 999,
val technicalMessage: String? = null,
override val message: String = "No offline data found"
) : Exception()
