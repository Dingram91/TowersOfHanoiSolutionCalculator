package TowersOfHanoiSolver;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class containing the entry point for the program that initializes the main scene.
 *
 * @author Dylan Ingram
 */
public class Main extends Application {

    /**
     * THe start method for initializing the gui scene. This method also sets up listeners for changes
     * to the window size so that the animations can by dynamically resized.
     *
     * @param primaryStage FXML required parameter for initializing the scene.
     * @throws Exception Generic exception.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("TowersOfHanoiSolver.fxml"));
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("TowersOfHanoiSolver.fxml"));
        Parent root = loader.load();
        Controller controllerRef = loader.getController();
        primaryStage.setTitle("Towers of Hanoi Solver");
        primaryStage.setScene(new Scene(root));

        // add listeners for changes to the height and width of the scene
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                Number newValue) {
                controllerRef.setScreenWith((Double) newValue);
            }
        });
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                Number newValue) {
                controllerRef.setScreenHeight();
            }
        });
        primaryStage.show();
    }

    /**
     * Main method to serve as the entry point for the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
