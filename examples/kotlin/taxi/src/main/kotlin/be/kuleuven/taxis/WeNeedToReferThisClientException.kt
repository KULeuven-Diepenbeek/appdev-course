package be.kuleuven.taxis

// Exception is the Kotlin exception, not the checked java.lang.Exception!
class WeNeedToReferThisClientException(msg: String) : Exception(msg) {
}