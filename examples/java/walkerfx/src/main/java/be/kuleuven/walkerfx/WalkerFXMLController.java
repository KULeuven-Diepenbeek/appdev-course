package be.kuleuven.walkerfx;

import be.kuleuven.walkerfx.model.Walker;
import be.kuleuven.walkerfx.view.WalkerView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class WalkerFXMLController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootPanel;

    @FXML
    private Label coords;

    private Walker model;
    private WalkerView view;

    @FXML
    void initialize() {
        model = new Walker(50, 50);
        view = new WalkerView(model);
        rootPanel.getChildren().add(view);
        view.setOnKeyPressed(this::move);
        view.setFocusTraversable(true);
    }

    public void move(KeyEvent e) {
        switch (e.getCode()) {
            case RIGHT:
            case F:
                model.goRight();
                update();
                break;
            case LEFT:
            case Q:
                model.goLeft();
                update();
                break;
            case UP:
                model.goUp();
                update();
                break;
            case DOWN:
                model.goDown();
                update();
                break;
        }
    }

    public void update() {
        coords.setText("(" + model.getX() + ", " + model.getY() + ")");
        view.update();
    }
}
