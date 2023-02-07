package com.bms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main: the starting point of the application
 */
public class Main extends Application {
    /**
     * The method used to start the threads
     * @param stage the main stage
     * @throws IOException exception if fxml files could not be found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController controller = fxmlLoader.getController();
        controller.init();
        stage.setTitle("BMS");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starting point
     * @param args arguments
     */
    public static void main(String[] args) {
        launch();
    }
}