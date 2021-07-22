package be.kuleuven.walkerfx.view

import be.kuleuven.walkerfx.model.Walker
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class WalkerView(model: Walker) : Region() {
    val model = model

    // What's this? https://blog.mindorks.com/understanding-init-block-in-kotlin
    // I can't call update() in the "constructor" as that is just a one-liner in rule 8
    init {
        update()
    }

    fun update() {
        // note that I don't need to say "getChilden()" -- it's implicitly just a field
        children.clear()

        // note that Rectangle suddenly only accepts Doubles instead of Ints.
        // I can convert that using .toDouble() on an Int in Kotlin!
        val rh = Rectangle(model.x.toDouble(), model.y.toDouble(), 40.0, 30.0)
        rh.setFill(Color.AQUAMARINE)

        children.add(rh)
    }
}