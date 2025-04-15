package es.uji.al435138.graphic;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class JavaFXApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(); // Contenedor principal
        Label label = new Label("Hola mundo!"); // Texto no editable
        Button button = new Button("Un botón :D"); // Un botón, no hace nada
        layout.getChildren().addAll(label, button); // Añade elementos al contenedor
        layout.setAlignment(Pos.CENTER); // Centra los elementos
// Añade el contenedor a la escena, y la escena al escenario
        primaryStage.setScene(new Scene(layout, 200, 100));
        primaryStage.setTitle("JavaFXApp"); // Título de ventana
        primaryStage.show(); // Muestra la ventana
    }
}
