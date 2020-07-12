/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author usama
 */
public class WrongInput {

    static boolean answer;

    public static boolean display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label();
        label.setText("Wrong Input!");
        Button close = new Button("Close");
        close.setOnAction(e -> {
            stage.close();
        });

        //clicking on x sign on prompt
        stage.setOnCloseRequest(null);

        /*  HBox hbox = new HBox();
        hbox.setMargin(yes, new Insets(20,20,20,20));
        hbox.setMargin(no, new Insets(20,20,20,20));
        hbox.getChildren().addAll(yes,no);
         */
        VBox vbox = new VBox(20);
        vbox.setMargin(label, new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, close);
        Scene scene = new Scene(vbox, 400, 150);
        stage.setScene(scene);
        stage.showAndWait();

        return answer;
        //VBox vbox = new VBox();

    }
}
