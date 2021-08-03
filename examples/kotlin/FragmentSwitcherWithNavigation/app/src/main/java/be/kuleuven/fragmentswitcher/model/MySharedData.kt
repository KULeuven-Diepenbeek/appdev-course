package be.kuleuven.fragmentswitcher.model

import kotlinx.serialization.Serializable

@Serializable
data class MySharedData(var name: String = "", var age: Int = 0) : java.io.Serializable