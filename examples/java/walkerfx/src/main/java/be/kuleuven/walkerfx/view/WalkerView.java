package be.kuleuven.walkerfx.view;

import be.kuleuven.walkerfx.model.Walker;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WalkerView extends Region {
    private final Walker model;

    public WalkerView(Walker model) {
        this.model = model;
        update();
    }

    public void update() {
        getChildren().clear();
        Rectangle rh = new Rectangle(model.getX(), model.getY(),
                40, 30);
        rh.setFill(Color.AQUAMARINE);

        getChildren().add(rh);
    }
}
