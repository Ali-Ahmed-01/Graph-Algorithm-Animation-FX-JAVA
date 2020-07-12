package GUI;

import BSTgraph_.Graph;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Popup;

public class EdgeLine extends Line {

    Popup popup;
    TextField EdgeValueInput;
    Text EdgeValue;
    HBox hbox = new HBox(10);

    EdgeLine() {
        super();
        popup = new Popup();
        EdgeValueInput = new TextField("Enter the Edge value!");
        EdgeValue = new Text();
        hbox.getChildren().addAll(EdgeValueInput);
        popup.getContent().add(hbox);

        EdgeValueInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String weight = EdgeValueInput.getText();
                if (checkEnteredData(weight)) {
                    EdgeValue.setText(weight);
                    Graph.getGraph().setWeight(itsParent.nameOfNode, itsChild.nameOfNode, weight);
                    popup.hide();
                } else {

                    WrongInput.display();
                    // System.out.println("Text : "+name.getText());
                    EdgeValue.setText("Wrong vvalue set hogai");
                    popup.hide();
                }
            }

        });

    }

    int count = 0;

    private boolean checkEnteredData(String text) {
        if (text.length() > 1) {
            return false;
        }
        if (!text.matches("[a-zA-Z]+")) {
            return true;
        } else {
            return false;
        }
    }

    NodeCircle itsParent, itsChild;

    void setItsParent(NodeCircle parent) {
        itsParent = parent;
    }

    void setItsChild(NodeCircle child) {
        itsChild = child;
    }

}
