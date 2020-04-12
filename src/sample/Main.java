package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private Game game = new Game();
    private Scene scene;
    private Group gameUIGroup = new Group();
    private Group rootGroup = new Group();
    private Label pauseLabel = new Label("");
    private boolean paused = false;

    private FileWriter logFile;

    @Override
    public void start(Stage primaryStage) throws Exception{
        logFile = new FileWriter("log.txt");
        primaryStage.setTitle("Game of Life");
        game.loadCells();
        renderBoard();
        renderCells();
        game.update();
        gameUIGroup.getChildren().add(pauseLabel);
        rootGroup.getChildren().add(gameUIGroup);
        rootGroup.getChildren().add(pauseLabel);
        scene = new Scene(rootGroup, 500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
        Thread thread = new Thread(new Task());
        thread.start();
        renderBoard();
        renderCells();
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key)->{
            if (key.getCode()== KeyCode.SPACE){
                System.out.println("game paused or unpaused");
                paused = !paused;
                if (!paused){
                    pauseLabel.setText("");
                } else pauseLabel.setText("PAUSED");
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                spawnCell(event.getSceneX(), event.getSceneY());
            }
        });
    }

    private void renderCells(){
        for (int i = 0; i < Game.boardX; i++) {
            for (int j = 0; j < Game.boardY; j++) {
                if (game.getCells()[i][j].isAlive()){
                    Circle circle = new Circle((i+1)*50-25,(j+1)*50-25,25);
                    circle.setFill(Color.SALMON);
                    gameUIGroup.getChildren().add(circle);
                }
            }
        }
    }

    private void renderBoard(){
        boolean shouldBeGray;
        for (int i = 0; i < Game.boardX; i++) {
            if (i%2==0) shouldBeGray=true;
            else shouldBeGray=false;
            for (int j = 0; j < Game.boardY; j++) {
                Rectangle rectangle = new Rectangle(i*50,j*50,50,50);
                if (shouldBeGray) {
                    rectangle.setFill(Color.GRAY);
                    shouldBeGray=false;
                } else {
                    rectangle.setFill(Color.DARKSLATEGRAY);
                    shouldBeGray=true;
                }
                gameUIGroup.getChildren().add(rectangle);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    private class Task implements Runnable{

        @Override
        public void run() {
            while (true){
                while (!paused){
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    game.update();
                    for (int i = 0; i < Game.boardX; i++) {
                        for (int j = 0; j < Game.boardY; j++) {
                            game.getCells()[i][j].update();
                        }
                    }
                    try {
                        logFile.write(game.getCellInformation());
                    } catch (IOException ignored) {}
                    //to access javafx application elements this must be here
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            renderBoard();
                            renderCells();
                        }
                    });
                    System.out.println("thread render now :)");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void spawnCell(double xMouse, double yMouse){
        int x = (int)xMouse/50;
        int y = (int)yMouse/50;
        System.out.println("X: "+x+" Y:"+y);
        game.getCells()[x][y].setAlive(true);
        renderBoard();
        renderCells();
    }
}
