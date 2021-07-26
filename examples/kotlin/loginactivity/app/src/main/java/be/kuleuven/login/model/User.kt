package be.kuleuven.login.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String) : java.io.Serializable {
}
