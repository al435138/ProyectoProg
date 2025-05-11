package es.uji.al435138.recommender.vista;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.controlador.Controlador;
import es.uji.al435138.recommender.modelo.Modelo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.List;

public class ImplementacionVista extends Application implements Vista {
    private Controlador controlador;

    // Componentes principales
    private ComboBox<String> comboMetodo;
    private ComboBox<String> comboDistancia;
    private ListView<String> listaCanciones;
    private Button btnRecomendar;
    private Label lblEstado;
    private Label lblResultado;
    private Modelo modelo;

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        // Configuración de icono
        ImageView icono = crearIcono("/images/nota.png");

        // Componentes de selección
        comboMetodo = new ComboBox<>(FXCollections.observableArrayList("Género", "Similitudes"));
        comboMetodo.setPromptText("Método recomendación");

        comboDistancia = new ComboBox<>(FXCollections.observableArrayList("Euclidean", "Manhattan"));
        comboDistancia.setPromptText("Tipo de distancia");

        // Lista y botón principal
        listaCanciones = new ListView<>();
        listaCanciones.setPrefHeight(200);

        btnRecomendar = new Button("Generar recomendación", icono);
        btnRecomendar.setDisable(true);

        // Etiquetas informativas
        lblEstado = new Label("Seleccione una canción");
        lblResultado = new Label();

        // Organización del layout
        layout.getChildren().addAll(
                new HBox(10, new Label("Método:"), comboMetodo),
                new HBox(10, new Label("Distancia:"), comboDistancia),
                new Label("Canciones disponibles:"),
                listaCanciones,
                btnRecomendar,
                lblEstado,
                lblResultado
        );

        configurarEventos();

        primaryStage.setScene(new Scene(layout, 450, 500));
        primaryStage.setTitle("Sistema de Recomendación Musical");
        primaryStage.show();
    }

    private ImageView crearIcono(String ruta) {
        try (InputStream input = getClass().getResourceAsStream(ruta)) {
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return imageView;
        } catch (Exception e) {
            System.err.println("Error cargando icono: " + e.getMessage());
            return new ImageView();
        }
    }

    private void configurarEventos() {
        // Actualización automática del estado del botón
        Runnable actualizarEstado = () -> {
            boolean seleccionCompleta =
                    comboMetodo.getValue() != null &&
                            comboDistancia.getValue() != null &&
                            listaCanciones.getSelectionModel().getSelectedItem() != null;

            actualizarEstadoBoton(seleccionCompleta);
        };

        comboMetodo.valueProperty().addListener((obs, oldVal, newVal) -> {
            try {
                controlador.metodoSeleccionadoCambiado(newVal);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (LikedItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            actualizarEstado.run();
        });

        comboDistancia.valueProperty().addListener((obs, oldVal, newVal) -> {
            controlador.distanciaSeleccionadaCambiada(newVal);
            actualizarEstado.run();
        });

        listaCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                controlador.cancionSeleccionadaCambiada();
                lblEstado.setText("Seleccionada: " + newVal);
            }
            actualizarEstado.run();
        });

        btnRecomendar.setOnAction(e -> controlador.generarRecomendaciones());
    }

    @Override
    public void actualizarEstadoBoton(boolean estado) {
        btnRecomendar.setDisable(!estado);
        btnRecomendar.setStyle(estado ?
                "-fx-background-color: #2E8B57; -fx-text-fill: white;" :
                "-fx-background-color: #cccccc; -fx-text-fill: #666666;");
    }

    @Override
    public void mostrarListaCanciones(List<String> canciones) {
        Platform.runLater(() -> {
            listaCanciones.getItems().clear();
            listaCanciones.getItems().addAll(canciones);
        });
    }

    // Resto de métodos de la interfaz Vista...
    @Override public void setControlador(Controlador controlador) { this.controlador = controlador; }

    @Override
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
        cargarCanciones();
    }

    private void cargarCanciones() {
        try {
            List<String> canciones = modelo.obtenerTodasCanciones();
            listaCanciones.setItems(FXCollections.observableArrayList(canciones));
        } catch (Exception e) {
            mostrarError("Error al cargar canciones: " + e.getMessage());
        }
    }

    @Override public void mostrarCanciones(List<String> canciones) { listaCanciones.setItems(FXCollections.observableArrayList(canciones)); }
    @Override public void mostrarRecomendaciones(List<String> recomendaciones) { lblResultado.setText("Recomendaciones: \n" + String.join("\n", recomendaciones)); }
    @Override public void mostrarError(String mensaje) { new Alert(Alert.AlertType.ERROR, mensaje).showAndWait(); }
    @Override public String getCancionSeleccionada() { return listaCanciones.getSelectionModel().getSelectedItem(); }

    public static void main(String[] args) { launch(args); }
}