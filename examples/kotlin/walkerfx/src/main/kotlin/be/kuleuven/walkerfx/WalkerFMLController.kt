package be.kuleuven.walkerfx

import be.kuleuven.walkerfx.model.Walker
import be.kuleuven.walkerfx.view.WalkerView
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.AnchorPane

class WalkerFMLController {

    // Why are these prefixed with 'lateinit'?
    // In Kotlin, I HAVE to give them a value as non-null variables.
    // However, I can only do this in the initialize function, so this is late-binding.
    @FXML
    lateinit var rootPanel: AnchorPane

    @FXML
    lateinit var coords: Label

    lateinit var model: Walker
    lateinit var view: WalkerView

    @FXML
    fun initialize() {
        model = Walker(50, 50)
        view = WalkerView(model)
        rootPanel.children.add(view)
        view.setOnKeyPressed(this::move)
        view.focusTraversableProperty().set(true)
    }

    fun move(e: KeyEvent) {
        // switching in Kotlin is 'when'. Cases are given a closure to execute.
        when (e.code) {
            KeyCode.RIGHT, KeyCode.F -> {
                model.goRight()
                update()
            }
            KeyCode.LEFT, KeyCode.Q -> {
                model.goLeft()
                update()
            }
            KeyCode.UP -> {
                model.goUp()
                update()
            }
            KeyCode.DOWN -> {
                model.goDown()
                update()
            }
        }
    }

    fun update() {
        // again, notice setText() is being replaced by directly modifying a property instead of a method call
        coords.text = "(" + model.x + ", " + model.y + ")"
        view.update()
    }
}