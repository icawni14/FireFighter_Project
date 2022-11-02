import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        Group root = new Group();
        Button restart = new Button("Restart");
        VBox buttons = new VBox();
        HBox total = new HBox();
        Grid grid = new Grid(1000,1000,20,20);

        root.getChildren().add(total);
        total.getChildren().add(buttons);
        total.getChildren().add(grid);
        buttons.getChildren().add(restart);
        restart.setOnMouseClicked(grid::restart);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        grid.repaint();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        sleep(50);
                        grid.model.activation();
                        grid.repaint();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}

