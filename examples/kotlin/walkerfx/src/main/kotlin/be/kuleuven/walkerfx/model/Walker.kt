package be.kuleuven.walkerfx.model

class Walker(x: Int, y: Int) {
    var x = x
        private set
    var y = y
        private set
    val MODIFIER = 5

    fun goUp() { y -= MODIFIER }
    fun goDown() { y += MODIFIER }
    fun goLeft() { x -= MODIFIER }
    fun goRight() { x += MODIFIER }
}