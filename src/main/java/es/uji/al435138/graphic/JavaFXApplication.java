package es.uji.al435138.graphic;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class JavaFXApplication extends Application {


    String      fontFamily  = "Arial";
    double      fontSize    = 18;
    FontWeight fontWeight  = FontWeight.BOLD;
    FontPosture fontPosture = FontPosture.ITALIC;

    Font font1 = Font.font(fontFamily);
    Font font2 = Font.font(fontSize);
    Font font3 = Font.font(fontFamily, fontSize);
    Font font4 = Font.font(fontFamily, fontWeight , fontSize);
    Font font5 = Font.font(fontFamily, fontWeight, fontSize);
    Font font6 = Font.font(fontFamily, fontWeight , fontPosture, fontSize);

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        VBox layout = new VBox(); // Contenedor principal

        Label label = new Label("Hola!"); // Texto no editable
        label.setFont(font6);

        InputStream input = getClass().getResourceAsStream("/images/nota.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);       // Ancho deseado
        imageView.setFitHeight(30);      // Alto deseado
        imageView.setPreserveRatio(true); // Mantiene la proporción original
        Button button = new Button("Genera una canción :D", imageView); // Un botón, no hace nada
        button.setFont(font5);

        ObservableList<String> recomendacion = FXCollections.observableArrayList("Género", "Similitudes");
        ComboBox<String> combo = new ComboBox<>(recomendacion);

        ObservableList<String> distancia = FXCollections.observableArrayList("Euclidean", "Manhattan");
        ComboBox<String> combo1 = new ComboBox<>(distancia);

        CheckBox checkBox1 = new CheckBox("Recomendar por géneros");
        HBox hbox1 = new HBox(checkBox1);
        CheckBox checkBox2 = new CheckBox("Recomendar por canciones");
        HBox hbox2 = new HBox(checkBox2);

        Label label2 = new Label(""); // Texto no editable
        label2.setFont(font6);


        layout.getChildren().addAll(combo, label, button, hbox1, hbox2, label2); // Añade elementos al contenedor
        layout.setAlignment(Pos.CENTER); // Centra los elementos

        button.setOnAction(actionEvent -> {
            label2.setText("Esta es la canción recomendada");
        });

        // Añade el contenedor a la escena, y la escena al escenario
        primaryStage.setScene(new Scene(layout, 400, 300));
        primaryStage.setTitle("Recomendación de canciones"); // Título de ventana
        primaryStage.show(); // Muestra la ventana
    }
}
