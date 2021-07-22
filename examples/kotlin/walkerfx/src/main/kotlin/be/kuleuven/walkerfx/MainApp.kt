package be.kuleuven.walkerfx

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class MainApp : Application() {

    override fun start(stage: Stage?) {
        var scene = Scene(loadFXML("wandelaarFXML"), 640.0, 480.0)
        stage!!.setScene(scene)
        stage.show()
    }

    private fun loadFXML(fxml: String): Parent {
        val resource = this::class.java.getResource("$fxml.fxml")
        var loader = FXMLLoader(resource)
        return loader.load()
    }
}

fun main(args: Array<String>) {
    Application.launch(MainApp::class.java, *args)
}