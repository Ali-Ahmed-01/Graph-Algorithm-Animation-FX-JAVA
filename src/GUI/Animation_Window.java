package GUI;

import Algorithms.Astar_algorithm;
import Algorithms.BFS;
import Algorithms.DFS;
import Algorithms.GreedyAlgorithm;
import Algorithms.IDS;
import Algorithms.UCS_Algorithm;
import Algorithms.hillClimbing;
import Algorithms.localBeam;
import BSTgraph_.Edge;
import BSTgraph_.Graph;
import BSTgraph_.Tree;
import BSTgraph_.vertex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import DS.Vqueue;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Animation_Window {

    static Pane pane;
    static int x = 900, y = 600;
    static int iniY;
    static ArrayList<DrawAbleGraph> drawAbleGraph;
    static int actuallayer;
    static ArrayList<circles> circle;
    static boolean pauseflag;
    static boolean Stopflag;
    static boolean playflag;
    static String algoSelected;

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Animation");
        window.setOnCloseRequest(e
                -> {
            e.consume();
            if (Exit_Confirmation.display()) {
                window.close();
            }
        });
        //Buttons
        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(e
                -> {
            System.exit(0);
            if (Exit_Confirmation.display()) {
                window.close();
            }
        });
//        Button generate = new Button();
//        generate.setText("Generate");
//        Button heuristic__ = new Button();
//        heuristic__.setText("Heuristic");
        Button play = new Button("Play");

        play.setOnAction(e
                -> {
            playflag = true;
            if (algoSelected()) {
            } else {
                JOptionPane.showMessageDialog(null, "empty", "can't", JOptionPane.ERROR_MESSAGE);
            }
        });

        pauseflag = true;
        Button pause = new Button("Pause");
        pause.setOnAction(e
                -> {
            pauseflag = false;
        });

        Stopflag = true;
        Button stop = new Button("Stop");
        stop.setOnAction(e
                -> {
            Stopflag = false;
            pauseflag = false;
        });
        Button replay = new Button("Replay");
        replay.setOnAction(e
                -> {
            if (!Stopflag) {
                pauseflag = true;
                if (algoSelected()) {

                }
            }
        });
        Button nextStep = new Button("Next");
        nextStep.setOnAction(e
                -> {
            next();
        });

        Button previousStep = new Button("Previous");
        previousStep.setOnAction(e
                -> {
            previous();
        });

        //
        //  closeButton.setMinWidth(80);
        // generate.setMinWidth(80);
        //heuristic.setMinWidth(80);
        //Container for buttons
        HBox menu = new HBox();
        //Properties for button
        menu.setMargin(closeButton, new Insets(20, 20, 20, 20));
//        menu.setMargin(generate, new Insets(20, 20, 20, 20));
//        menu.setMargin(heuristic__, new Insets(20, 20, 20, 20));
        menu.setMargin(play, new Insets(20, 20, 20, 20));
        menu.setMargin(pause, new Insets(20, 20, 20, 20));
        menu.setMargin(stop, new Insets(20, 20, 20, 20));
        menu.setMargin(replay, new Insets(20, 20, 20, 20));
        menu.setMargin(nextStep, new Insets(20, 20, 20, 20));
        menu.setMargin(previousStep, new Insets(20, 20, 20, 20));

        //Adding nodes to container
//        menu.getChildren().addAll(generate, heuristic__, closeButton, play, pause, stop, replay, previousStep, nextStep);
        menu.getChildren().addAll(closeButton, play, pause, stop, replay, previousStep, nextStep);
        /**
         * ***********
         */

        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "A*",
                        "greedy best first",
                        "breadth first",
                        "depth first",
                        "iterative deepening",
                        "uniform cost",
                        "local beam", "hill Climbing",
                        "simulated annealing"
                );

        algoSelected = "";
        final ComboBox comboBox = new ComboBox(options);
        comboBox.setValue("Select the Algorithms");
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                algoSelected = t1;
                System.out.println(t1);
            }
        });

        VBox upperPortion = new VBox();
        upperPortion.getChildren().addAll(menu, comboBox);

        BorderPane layout = new BorderPane();;
        pane = new Pane();
        pane.setStyle("-fx-background-color:red");
        pane.setMaxHeight(600);
        pane.setMaxWidth(900);
        iniY = 50;

        treeGenrate();
        //------------------------------------------------
        layout.setTop(upperPortion);
        layout.setCenter(pane);
        Scene scene = new Scene(layout, 1024, 700);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.showAndWait();
    }
    static ArrayList<String> AllNodesBYname;

    public static void treeGenrate() {
        drawAbleGraph = new ArrayList<>();
        AllNodesBYname = new ArrayList<>();
        circle = new ArrayList<>();

        int layer = 1;
        int clayer = 0;
        actuallayer = 0;
        vertex vr = Tree.getBST().getNode(Tree.getBST().root.data.nodename);
        vertex temp;
        if (vr != null) {
            Vqueue Q = new Vqueue();
            Q.enqueue(vr);
            while (Q.isEmpty() == false) {
                layer--;
                temp = Q.dequeue();
                temp.flag = true;
                AllNodesBYname.add(temp.nodename);

                ArrayList<edgeAndsitsWeight> circlesAnditsInfo = new ArrayList<>();
                for (Edge adjEdge : temp.getNeighbours()) {
                    if (adjEdge.chid.flag == false) {
                        circlesAnditsInfo.add(new edgeAndsitsWeight(adjEdge.chid.nodename, adjEdge.weight));
                        clayer++;
                        Q.enqueue(adjEdge.chid);
                    }
                }
                drawAbleGraph.add(new DrawAbleGraph(temp.nodename, actuallayer, circlesAnditsInfo));
                if (layer == 0) {
                    layer = clayer;
                    if (clayer != 0) {
                        actuallayer++;
                    }
                    clayer = 0;
                }
            }
        }
        drawCircle__();
    }

    public static class DrawAbleGraph {

        String name;
        int actuallayer;
        ArrayList<edgeAndsitsWeight> edgeAndItsWgt;
        public int fromX, fromY;
        int limit;

        public DrawAbleGraph(String name, int actuallayer, ArrayList<edgeAndsitsWeight> nameOfCircles) {
            this.name = name;
            this.actuallayer = actuallayer;
            this.edgeAndItsWgt = nameOfCircles;
        }
    }

    public static class edgeAndsitsWeight {

        public String name;
        public int wgt;

        public edgeAndsitsWeight(String name, int wgt) {
            this.name = name;
            this.wgt = wgt;
        }

    }

    public static class circles {

        String name;
        Circle circle;

        public circles(String name, Circle circle) {
            this.name = name;
            this.circle = circle;
        }
    }

    static int[] totalnodesOnAlayer;

    public static void drawCircle__() {

        totalnodesOnAlayer = new int[actuallayer + 1];
        int sum;
        for (int i = 0; i <= actuallayer; i++) {
            sum = 0;
            for (int j = 0; j < drawAbleGraph.size(); j++) {
                if (drawAbleGraph.get(j).actuallayer == i) {
                    sum++;
                }
            }
            totalnodesOnAlayer[i] = sum;
            if (sum > 0) {
                int whichNode = 1;
                int eachBox = x / sum;
                int midle = eachBox / 2;
                for (int j = 0; j < drawAbleGraph.size(); j++) {
                    if (drawAbleGraph.get(j).actuallayer == i) {
                        int X_ = (eachBox * (whichNode++)) - midle;
                        drawAbleGraph.get(j).fromX = X_;
                        drawAbleGraph.get(j).fromY = iniY;
                        Circle circle = new Circle(X_, iniY, 20);//x,y,radius
                        circle.setFill(Color.WHEAT.brighter());
                        Text text = new Text(circle.getCenterX(), circle.getCenterY(), drawAbleGraph.get(j).name);
                        Animation_Window.circle.add(new circles(drawAbleGraph.get(j).name, circle));
                        pane.getChildren().addAll(circle, text);
                    }
                }
                iniY += 100;
            }
        }

        int lineCounts;
        boolean flag;
        for (int i = 0; i <= actuallayer; i++) {

            int totalLinesOfthislayer = 0;
            for (int j = 0; j < drawAbleGraph.size(); j++) {
                if (drawAbleGraph.get(j).actuallayer == i && drawAbleGraph.get(j).edgeAndItsWgt.size() > 0) {
                    int startX = drawAbleGraph.get(j).fromX;
                    int startY = drawAbleGraph.get(j).fromY;
                    lineCounts = 0;
                    int Draw = 0;
                    flag = false;
                    for (int k = 0; (k < drawAbleGraph.size() && ((i + 1) <= actuallayer) && Draw < drawAbleGraph.get(j).edgeAndItsWgt.size()); k++) {

                        if (drawAbleGraph.get(k).actuallayer == i + 1) {
                            if (lineCounts == totalLinesOfthislayer) {
                                flag = true;
                            }
                            lineCounts++;

                            if (flag) {
                                totalLinesOfthislayer++;
                                int endX = drawAbleGraph.get(k).fromX;
                                int endY = drawAbleGraph.get(k).fromY;

                                Line line = new Line(startX, startY, endX, endY);
                                line.setFill(Color.WHEAT.brighter());
                                Text text = new Text((startX + endX) / 2, (startY + endY) / 2, drawAbleGraph.get(j).edgeAndItsWgt.get(Draw).wgt + "");
                                pane.getChildren().addAll(line, text);
                                Draw++;
                            }
                        }
                    }
                }
            }
        }

//        runAlgo();
//        System.out.println("circles-----------------");
//        System.out.println("circle.size(); " + circle.size());
//        for (int i = 0; i < circle.size(); i++) {
//            System.out.println(circle.get(i).name);
//        }
//        System.exit(0);
    }

    static ArrayList<Circle> CallNodes, pathNodes;

    public static void findAllpathNodesFORUninformed(String[] exeNode) {
        CallNodes = new ArrayList<>();
        pathNodes = new ArrayList<>();

        for (String nodename : exeNode) {
            for (circles cs : circle) {
                if (nodename.equals(cs.name)) {
                    CallNodes.add(cs.circle);
                    break;
                }
            }
        }
    }

    public static void findAllpathNodes(String[] exeNode, String[] nodePath) {
        CallNodes = new ArrayList<>();

        for (String nodename : exeNode) {
            for (circles cs : circle) {
                if (nodename.equals(cs.name)) {
                    CallNodes.add(cs.circle);
                    break;
                }
            }
        }

        pathNodes = new ArrayList<>();

        for (String nodename : nodePath) {
            for (circles cs : circle) {
                if (nodename.equals(cs.name)) {
                    pathNodes.add(cs.circle);
                    break;
                }
            }
        }
//        
    }

    private static void playAuto() {

        ImplementsRunnable rc = new ImplementsRunnable();
        Thread t1 = new Thread(rc);
        t1.start();
    }

    static int pCnode = 0;
    static int preC = 0;

    public static void next() {
        if (pCnode < CallNodes.size()) {
            CallNodes.get(pCnode).setFill(Color.YELLOW);
            preC = pCnode;
            pCnode++;
            if (pCnode == CallNodes.size()) {
                for (Circle cs : pathNodes) {
                    cs.setFill(Color.GREEN.brighter());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Stop", "Complete", JOptionPane.ERROR_MESSAGE);

        }
    }

    public static void previous() {
        if (preC > 0 && preC < CallNodes.size()) {
            CallNodes.get(preC).setFill(Color.WHEAT);
            pCnode = preC;
            preC--;

        } else if (pCnode >= CallNodes.size()) {
            pCnode = preC = CallNodes.size() - 1;
            CallNodes.get(preC).setFill(Color.WHEAT);
            pCnode--;

        } else {
            JOptionPane.showMessageDialog(null, "initial Node", "Can't go back", JOptionPane.ERROR_MESSAGE);

        }
    }

    static class ImplementsRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("PLAY");

            for (int i = 0; i < CallNodes.size() && pauseflag; i++) {
                try {
                    preC = pCnode = i;
                    pCnode++;
                    CallNodes.get(i).setFill(Color.YELLOW);
                    // thread to sleep for 1000 milliseconds
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            if (!pathNodes.isEmpty() && pauseflag) {
                previous();

                pathNodes.forEach((cs) -> {
                    cs.setFill(Color.GREEN.brighter());
                });
            }

        }
    }

    static String WindownameForHUR = "Heuristics";
    static boolean ckInput;

    public static boolean SetHeuristic_vales() {

        TextField[] hueristic = new TextField[AllNodesBYname.size()];
        Label[] Labelhur = new Label[AllNodesBYname.size()];

        //------------------------------------------------
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(WindownameForHUR);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        for (int i = 0; i < hueristic.length; i++) {

            hueristic[i] = new TextField();
            hueristic[i].setPromptText("To");

            Labelhur[i] = new Label(AllNodesBYname.get(i));

            gridPane.add(hueristic[i], 0, i);
            gridPane.add(new Label("To:"), 1, i);
            gridPane.add(Labelhur[i], 2, i);

            // Request focus on the username field by default.
            Platform.runLater(() -> hueristic[0].requestFocus());

            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    //when okay pressed
                    return new Pair<>("", "");
                }
                return null;
            });
        }

        dialog.getDialogPane().setContent(gridPane);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent((Pair<String, String> pair) -> {

            Graph g = Graph.getGraph();
            ckInput = true;
            for (int i = 0; i < hueristic.length; i++) {

                if (hueristic[i].getText().matches("[a-zA-Z]+") || hueristic[i].getText().equals("")) {
                    ckInput = false;
                    break;
                }
            }
            if (ckInput) {
                for (int i = 0; i < hueristic.length; i++) {
                    g.place_heuristic_vales(Labelhur[i].getText(), hueristic[i].getText());
                }
            } else {
                JOptionPane.showMessageDialog(null, "wrong input", "", JOptionPane.ERROR_MESSAGE);
            }
        });
        return ckInput;
    }

    public static void splitDataForAnimation(String path, String exe) {
        String[] pathnodes = path.split(",");
        String[] exeNodes = exe.split(",");

        findAllpathNodes(exeNodes, pathnodes);
    }

    public static class IDSthread extends Thread {

        ArrayList<String> allPath;

        public IDSthread(ArrayList<String> allPath) {
            this.allPath = allPath;
        }

        @Override
        public void run() {
            pathNodes = new ArrayList<>();
            for (String p : allPath) {
                for (int i = 0; i < circle.size(); i++) {
                    circle.get(i).circle.setFill(Color.WHEAT.brighter());
                }
                String[] exeNodes = p.split(",");
                //--------------------------------
                CallNodes = new ArrayList<>();
                for (String nodename : exeNodes) {
                    for (circles cs : circle) {
                        if (nodename.equals(cs.name)) {
                            CallNodes.add(cs.circle);
                            break;
                        }
                    }
                }

                for (int i = 0; i < CallNodes.size() && pauseflag; i++) {
                    try {
                        CallNodes.get(i).setFill(Color.YELLOW);
                        // thread to sleep for 1000 milliseconds
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
                if (!pauseflag) {
                    break;
                }
            }
        }
    }
    static int K;

    public static int K_valueInput(String lab) {

        K = 0;

        //------------------------------------------------
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(WindownameForHUR);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField K_ = new TextField();

        gridPane.add(new Label(lab), 0, 0);
        gridPane.add(K_, 1, 0);

        // Request focus on the username field by default.
        Platform.runLater(() -> K_.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                //when okay pressed
                return new Pair<>(K_.getText(), "");
            }
            return null;
        });
        dialog.getDialogPane().setContent(gridPane);
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent((Pair<String, String> pair) -> {
            if (pair.getKey().matches("[0-9]+")) {
                K = Integer.parseInt(pair.getKey());
            }
        });
        return K;
    }

    public static boolean algoSelected() {
        boolean flag = false;
        for (int i = 0; i < circle.size(); i++) {
            circle.get(i).circle.setFill(Color.WHEAT.brighter());
        }

        if (algoSelected.equals("A*")) {
            WindownameForHUR = "Heuristics";
            if (SetHeuristic_vales()) {
                Astar_algorithm aStr = new Astar_algorithm();
                splitDataForAnimation(aStr.path, aStr.getExecution());
                playAuto();
            }

            return true;

        } else if (algoSelected.equals("greedy best first")) {
            WindownameForHUR = "Heuristics";
            if (SetHeuristic_vales()) {
                GreedyAlgorithm greedyBestFirst = new GreedyAlgorithm();
                splitDataForAnimation(greedyBestFirst.path, greedyBestFirst.getExecution());
                playAuto();
            }
            return true;
        } else if (algoSelected.equals("uniform cost")) {
            UCS_Algorithm UCS = new UCS_Algorithm();
            splitDataForAnimation(UCS.path, UCS.getExecution());
            playAuto();
            return true;

        } else if (algoSelected.equals("breadth first")) {
            BFS bfs = new BFS();
            String[] exeNodes = bfs.getExecution().split(",");
            findAllpathNodesFORUninformed(exeNodes);
            playAuto();
            return true;
        } else if (algoSelected.equals("iterative deepening")) {
            IDS ids = new IDS(actuallayer + 1);
            if (!ids.flag) {
                ArrayList<String> allPath = ids.allPath;
                (new Thread(new IDSthread(allPath))).start();
            }
            return true;

        } else if (algoSelected.equals("hill Climbing")) {
            WindownameForHUR = "Objective function for each node";
            if (SetHeuristic_vales()) {
                hillClimbing HC = new hillClimbing();
                if (!HC.flag) {
                    ArrayList<String> allPath = HC.allPath;
                    (new Thread(new IDSthread(allPath))).start();
                }
            }
            return true;

        } else if (algoSelected.equals("depth first")) {
            System.out.println(algoSelected);
            DFS dfs = new DFS();
            String[] exeNodes = dfs.getExecution().split(",");
            findAllpathNodesFORUninformed(exeNodes);
            playAuto();
            return true;
        } else if (algoSelected.equals("local beam")) {
            WindownameForHUR = "Objective function for each node";
            if (SetHeuristic_vales()) {
                localBeam lbs = new localBeam(K_valueInput("Insert the other nodes to be expanded with the root"));
                if (!lbs.flag) {
                    ArrayList<String> allPath = lbs.allPath;
                    (new Thread(new IDSthread(allPath))).start();
                }
            }
            return true;
        } else if (algoSelected.equals("simulated annealing")) {
            WindownameForHUR = "Objective function for each node";
            if (SetHeuristic_vales()) {
                WindownameForHUR = "percentage value";
                localBeam SA = new localBeam(K_valueInput("insert the percentage value for the algorithm"));
                if (!SA.flag) {
                    ArrayList<String> allPath = SA.allPath;
                    (new Thread(new IDSthread(allPath))).start();
                }
            }
            return true;
        }
        return flag;
    }
}
