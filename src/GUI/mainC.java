package GUI;

import BSTgraph_.Graph;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import java.io.File;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class mainC extends Application {

    Button save;
    Button Displayalgorithms;
    Button open;
    Button button;
    Button generate;
    Button heuristic;
    Button objective;
    Button clear;
    Button add;
    Button remove;
    Button backward;
    Button forward;

    Stage window;
    Scene scene;
    Scene scene2;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    VBox toolbox;
    BorderPane layout;

    boolean removeCheck;

    NodeCircle circle;
    EdgeLine line;

    boolean backwardflag, forwardflag;

    static NodeCircle parent = null;
    public static String goal = null;

    double midPointX;//edge ki value line k center mai show karanay k wastay
    double midPointY;//edge ki value line k center mai show karanay k wastay

    Graph graph;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        nodeNameAsNUM = 0;
        graph = Graph.getGraph();

        settingUpButtons();
        button.setOnAction((ActionEvent e) -> {
            if (goal != null) {
                Animation_Window.display();
            } else {
                JOptionPane.showMessageDialog(null, "Goal is not define for animation", "Important", JOptionPane.ERROR_MESSAGE);
            }
        });
        remove.setOnAction(e -> {
            removeCheck = true;
            scene.setCursor(Cursor.CROSSHAIR);
        });

        //isay bhe miss kara
        //HBox container1
        HBox menu1 = new HBox(20);
        menu1.setId("menu1");
        menu1.getChildren().addAll(save, open, button);
        //HBox container2
        HBox menu2 = new HBox(20);
        menu2.setId("menu2");
        menu2.getChildren().addAll(generate, heuristic, objective, clear, Displayalgorithms);
        //HBox container3
        HBox menu3 = new HBox(20);
        menu3.setId("menu3");
        menu3.getChildren().addAll(add, remove, backward, forward);

        VBox bar = new VBox(10);
        bar.setId("bar");
        bar.setPrefWidth(1024);
        bar.getChildren().addAll(menu1, menu2, menu3);
        layout = new BorderPane();

        layout.setTop(bar);

        //Pane is drawing area
        pane = new Pane();
        pane.setStyle("-fx-background-color:cyan");
        pane.setMaxHeight(400);
        pane.setMaxWidth(500);

        layout.setCenter(pane);

        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1) {//&& (e.getSource() instanceof Circle) == false) {
                    checkWhichEventHastoBePerformed(e, primaryStage);
                }
            }
        });

        // root.getChildren().add(layout);
        scene = new Scene(layout, 1024, 768);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Title");
        primaryStage.show();

    }

    public static void inputFilename() {

        //------------------------------------------------
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();

        dialog.setTitle("Enter file name");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField filename = new TextField();
        filename.setPromptText("To");

        gridPane.add(new Label("Enter the file name"), 0, 0);
        gridPane.add(filename, 1, 0);

        // Request focus on the username field by default.
        Platform.runLater(() -> filename.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                //when okay pressed
                return new Pair<>(filename.getText(), "");
            }
            return null;
        });

        dialog.getDialogPane().setContent(gridPane);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent((Pair<String, String> pair) -> {

            Graph.getGraph().graphPrint(pair.getKey().trim());
            JOptionPane.showMessageDialog(null, "File save", pair.getKey().trim(), JOptionPane.INFORMATION_MESSAGE);

        });
    }

    private void settingUpButtons() throws FileNotFoundException {
        //Menu Buttons
        Displayalgorithms = new Button();
        Displayalgorithms.setText("Display search algorithms");
        Displayalgorithms.setOnAction(e
                -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Search Algorithms");
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));
            gridPane.add(new Label("* Informed search algorithms are:"), 0, 0);
            gridPane.add(new Label("  A*"), 0, 1);
            gridPane.add(new Label("  Greedy best first"), 0, 2);
            gridPane.add(new Label("* Local search algorithms are:"), 0, 4);
            gridPane.add(new Label("  Hill climbing"), 0, 5);
            gridPane.add(new Label("  Simulated annealing"), 0, 6);
            gridPane.add(new Label("  Local beam"), 0, 7);
            gridPane.add(new Label("* Uninformed search algorithms are:"), 0, 9);
            gridPane.add(new Label("  Breadth first"), 0, 10);
            gridPane.add(new Label("  Depth first"), 0, 11);
            gridPane.add(new Label("  Iterative deepening"), 0, 12);
            gridPane.add(new Label("  Uniform cost"), 0, 13);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(" ", "");
                }
                return null;
            });
            dialog.getDialogPane().setContent(gridPane);
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent((Pair<String, String> pair) -> {
                pane.getChildren().clear();
                graph.clearAll();
            });
        });

        save = new Button();
        save.setText("Save");
        save.setOnAction(e
                -> {
            inputFilename();
        });

        open = new Button();
        open.setText("Open");
        open.setOnAction(e
                -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                //          System.out.println(selectedFile);
                graph.clearAll();
                graph.readGraphFromTextfile(selectedFile.toString());
                
                if (BSTgraph_.Tree.getBST().root.data.neighbours.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Root has no path to any other node\nCan't apply Algorithm", "Important", JOptionPane.ERROR_MESSAGE);
                } else {

                    do {
                        //------------------------------------------------
                        // Create the custom dialog.
                        Dialog<Pair<String, String>> dialog = new Dialog<>();

                        dialog.setTitle("GOAL NODE");

                        // Set the button types.
                        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        gridPane.setPadding(new Insets(20, 150, 10, 10));

                        TextField filename = new TextField();
                        filename.setPromptText("To");

                        gridPane.add(new Label("Enter the goal node name"), 0, 0);
                        gridPane.add(filename, 1, 0);

                        // Request focus on the username field by default.
                        Platform.runLater(() -> filename.requestFocus());

                        // Convert the result to a username-password-pair when the login button is clicked.
                        dialog.setResultConverter(dialogButton -> {
                            if (dialogButton == loginButtonType) {
                                //when okay pressed
                                return new Pair<>(filename.getText(), "");
                            }
                            return null;
                        });

                        dialog.getDialogPane().setContent(gridPane);

                        Optional<Pair<String, String>> result = dialog.showAndWait();

                        result.ifPresent((Pair<String, String> pair) -> {

                            goal = pair.getKey().trim();
                        });
                    } while (!BSTgraph_.Tree.getBST().isPresent(goal));

                }
                Animation_Window.display();
            }
        });
        //--------------------------

        //-------------------------
        button = new Button();
        button.setText("Go to animation area");
        generate = new Button();
        generate.setText("Generate");
        heuristic = new Button();
        heuristic.setText("Heuristic");
        objective = new Button();
        objective.setText("Objective");
        clear = new Button();
        clear.setText("Clear");
        clear.setOnAction(e
                -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("?");
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));
            gridPane.add(new Label("Are you sure you want do delete everything on the drawing area"), 0, 0);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(" ", "");
                }
                return null;
            });
            dialog.getDialogPane().setContent(gridPane);
            Optional<Pair<String, String>> result = dialog.showAndWait();
            result.ifPresent((Pair<String, String> pair) -> {
                pane.getChildren().clear();
                graph.clearAll();
            });
        });
        add = new Button();
        add.setText("add");
        remove = new Button();
        remove.setText("remove");

        FileInputStream input = new FileInputStream("left.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        backward = new Button("Previous", imageView);
        backwardflag = false;
        forwardflag = false;
        backward.setOnAction(e
                -> {
            if (circle != null) {
                pane.getChildren().remove(circle);
                graph.remove(circle.nameOfNode);
            }
            if (line != null) {
                pane.getChildren().remove(line);
            }

            backwardflag = true;
        });

        input = new FileInputStream("right.png");
        image = new Image(input);
        imageView = new ImageView(image);
        forward = new Button("Next", imageView);
        forward.setOnAction(e
                -> {
            if (backwardflag) {
                if (circle != null) {
                    pane.getChildren().add(circle);
                    graph.inserSingleNodeINTOGraph(circle.nameOfNode);

                }
                if (line != null) {
                    pane.getChildren().add(line);
                }
                backwardflag = false;
                forwardflag = true;
            }
        });

    }

    /*
     * Some Logical @params
     */
    Pane pane;
    int timesClick = 0;
    boolean oneClick, twoClick = false;
    boolean paneClick, circleClick = false;

    public void checkWhichEventHastoBePerformed(MouseEvent e, Stage primaryStage) {

        if (oneClick) {
            oneClick = false;
        } else if (!oneClick && timesClick == 1) {
            timesClick = 0;
        } else if (twoClick) {
            twoClick = false;
        } else if (!twoClick && timesClick == 2) {
            timesClick = 0;
        } else {
            drawCircle(e, primaryStage);
        }
    }
    int nodeNameAsNUM;

    public void drawCircle(MouseEvent e, Stage primaryStage) {
        // pane.setON
        double X = e.getX(); // remove pane's coordinate system here
        double Y = e.getY(); // remove pane's coordinate system here
        circle = new NodeCircle(X, Y);

        circle.name = new Text();
        pane.getChildren().add(circle.name);

        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.nameOfNode = "N" + (++nodeNameAsNUM);
        graph.inserSingleNodeINTOGraph(circle.nameOfNode);
        ///////////////////////////////////////
        circle.setOnMouseClicked((MouseEvent evt) -> {
            NodeCircle clickedNode = (NodeCircle) evt.getSource();

            if (evt.getButton() == MouseButton.SECONDARY) {
                clickedNode.contextMenu.show(clickedNode, evt.getScreenX(), evt.getScreenY());
            } else if (evt.getButton() == MouseButton.PRIMARY && removeCheck == true && evt.getClickCount() == 1) {
                evt.consume();
                pane.getChildren().remove(evt.getSource());
                removeCheck = false;
                scene.setCursor(Cursor.DEFAULT);
                graph.remove(clickedNode.nameOfNode);

            } else if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
                twoClick = true;
                timesClick = 2;

                clickedNode.name.setText("");
                clickedNode.name.toFront();
                clickedNode.popup.setX(evt.getScreenX());
                clickedNode.popup.setY(evt.getSceneY());
                clickedNode.popup.show(primaryStage);
                clickedNode.name.setX(clickedNode.getCenterX());
                clickedNode.name.setY(clickedNode.getCenterY());

                // circle.popup.setAutoFix(false);
                // circle.popup.setHideOnEscape(true);
            } else {
                oneClick = true;
                timesClick = 1;
            }
        });

        /////////////////////////////////////
        if (parent != null) {
            line = new EdgeLine();
            line.setStartX(parent.getCenterX());
            line.setStartY(parent.getCenterY());
            line.setEndX(circle.getCenterX());
            line.setEndY(circle.getCenterY());
            line.setItsParent(parent);
            line.setItsChild(circle);
            graph.insertGraph(parent.nameOfNode, circle.nameOfNode, 0);
            //Line ka double click yaha handle hoga
            //
            line.setStrokeWidth(5.0);
            line.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent evt) {
                    EdgeLine C_line = (EdgeLine) evt.getSource();
                    if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 2) {
                        twoClick = true;

                        midPointX = (C_line.getStartX() + C_line.getEndX()) / 2;
                        midPointY = (C_line.getStartY() + C_line.getEndY()) / 2;

                        C_line.popup.setX(evt.getScreenX());
                        C_line.popup.setY(evt.getSceneY());
                        C_line.EdgeValue.setX(midPointX);
                        C_line.EdgeValue.setY(midPointY);
                        C_line.popup.show(primaryStage);

                        timesClick = 2;
                    } else {
                        oneClick = true;
                        timesClick = 1;
                    }
                }
            });
            pane.getChildren().addAll(line, line.EdgeValue);
        }
        /////////////////////////////////////
        if (removeCheck == false) {
            pane.getChildren().addAll(circle);
        }
        removeCheck = false;
        scene.setCursor(Cursor.DEFAULT);
    }
}
