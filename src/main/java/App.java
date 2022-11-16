import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class App extends Application {

    boolean isInPause = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Button restart = new Button("Restart");
        Button switchPause = new Button("Pause");
        VBox buttons = new VBox();
        HBox total = new HBox();
        Grid grid = new Grid(700,700,20,20);

        root.getChildren().add(total);
        total.getChildren().add(buttons);
        total.getChildren().add(grid);
        buttons.getChildren().add(restart);
        buttons.getChildren().add(switchPause);
        restart.setOnMouseClicked(grid::restart);
        switchPause.setOnMouseClicked((value)->isInPause = !isInPause);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        grid.repaint();

        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        threadPoolExecutor.scheduleAtFixedRate(() -> {
            if(!isInPause) {
                grid.model.activation();
                grid.repaint();
            }
        }, 0, 50 , TimeUnit.MILLISECONDS);
    }
}

