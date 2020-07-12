package GUI;

import BSTgraph_.Graph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

public class NodeCircle extends Circle {

    boolean goal;

    ContextMenu contextMenu;
    final MenuItem item1;
    final MenuItem item2;
    public String nameOfNode;

    TextField nodeName;
    Text name;

    Popup popup;

    HBox hbox = new HBox(10);

    NodeCircle(double X, double Y) {
        super(X, Y, 20);

        popup = new Popup();

        popup.setX(this.getCenterX());
        popup.setY(this.getCenterY());

        nodeName = new TextField("Enter the node name!");
        hbox.getChildren().addAll(nodeName);
        popup.getContent().add(hbox);

        contextMenu = new ContextMenu();
        item1 = new MenuItem("Parent");
        item2 = new MenuItem("Goal");

        contextMenu.getItems().addAll(item1, item2);

        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (mainC.parent != null) {
                    mainC.parent.setFill(Color.WHITE);
                    mainC.parent.setStroke(Color.BLACK);
                }
                NodeCircle.this.setFill(Color.PINK);
                NodeCircle.this.setStroke(Color.BLACK);
                mainC.parent = NodeCircle.this;
            }
        });

        item2.setOnAction(e -> {
            goal = true;
            mainC.goal = this.nameOfNode;
            System.out.println("goal");
        });

        nodeName.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER) {
                String nameSet = nodeName.getText();
                if (checkEnteredData(nameSet)) {

                    name.setText(nameSet);
                    Graph.getGraph().changeNameOFnode(this.nameOfNode , nameSet);
                    nameOfNode = nameSet;
                    popup.hide();
                } else {

                    WrongInput.display();
                    // System.out.println("Text : "+name.getText());
                    name.setText("Wrong Name set hogaya hai");
                    popup.hide();
                }
            }
        });
    }

    private boolean checkEnteredData(String text) {
        if (text.length() > 6) {
            return false;
        }
        if (text.matches("[a-zA-Z]+")) {
            return true;
        } else {
            return false;
        }
    }
}
