package es.uji.al435138.recommender.vista;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.controlador.Controlador;
import es.uji.al435138.recommender.modelo.Modelo;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class ImplementacionVista implements Vista {
    private Controlador controlador;
    private final Stage stage;
    private Modelo modelo;

    // Componentes principales
    private ListView<String> listaCanciones;
    private ListView<String> listaRecomendaciones;
    private Button btnRecomendar;
    private TextField numRecs;
    private ComboBox<String> cbRecomendacion;
    private ComboBox<String> cbDistancia;

    public ImplementacionVista(Stage stage) {
        this.stage = stage;
    }

    public void setModelo(final Modelo modelo) {
        this.modelo = modelo;
    }

    public void setControlador(final Controlador controlador) {
        this.controlador = controlador;
    }

    public void creaGUI() throws Exception {
        BorderPane root = new BorderPane();

        // Título superior
        HBox tituloSuperior = new HBox();
        tituloSuperior.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label tituloPrincipal = new Label("Algoritmos que entienden tu ritmo.");
        tituloPrincipal.setFont(new Font("Impact", 30));
        tituloPrincipal.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        tituloSuperior.getChildren().add(tituloPrincipal);

        // Panel central y derecho en un HBox
        VBox panelCentral = null;
        try {
            panelCentral = crearPanelCentral();
        } catch (LikedItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        VBox panelDerecho = crearPanelDerecho();

        HBox contenedorListas = new HBox(panelCentral, panelDerecho);
        HBox.setHgrow(panelCentral, Priority.ALWAYS);
        HBox.setHgrow(panelDerecho, Priority.ALWAYS);
        panelCentral.setPrefWidth(600); // Ajusta el ancho inicial
        panelDerecho.setPrefWidth(600); // Ajusta el ancho inicial

        root.setTop(tituloSuperior);
        root.setCenter(contenedorListas);

        // Estilo general
        root.setStyle("-fx-background-color: #121212;");

        // Pie de página
        HBox pieDePagina = new HBox();
        pieDePagina.setStyle("-fx-padding: 10; -fx-alignment: center;");
        Label marca = new Label("Programación Avanzada 2025");
        marca.setFont(new Font("Roboto", 12));
        marca.setStyle("-fx-text-fill: white;");
        pieDePagina.getChildren().add(marca);

        root.setBottom(pieDePagina);

        Scene escena = new Scene(root, 1200, 700);
        stage.setScene(escena);
        stage.show();
    }

    private VBox crearPanelCentral() throws LikedItemNotFoundException, Exception {
        VBox panelCentral = new VBox(20);
        panelCentral.setStyle("-fx-padding: 20; -fx-alignment: top-left;"); // Alineación a la izquierda

        // Título "Canciones disponibles"
        Label titulo = new Label("Canciones disponibles");
        titulo.setFont(new Font("Roboto", 20)); // Cambiado a Roboto
        titulo.setStyle("-fx-font-weight: bold; -fx-translate-y: -10;"); // Negrita y desplazamiento vertical
        titulo.setTextFill(Color.WHITE);

        List<String> canciones = modelo.cargarListaCanciones();
        listaCanciones = new ListView<>(FXCollections.observableArrayList(canciones));
        listaCanciones.setStyle("-fx-background-color: #282828; -fx-text-fill: white;");

        // Alternar colores de fondo
        listaCanciones.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (isSelected()) {
                        setStyle("-fx-background-color: #ff9800; -fx-text-fill: black;"); // Fondo naranja y texto negro
                    } else if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: #6739da; -fx-text-fill: white;"); // Morado
                    } else {
                        setStyle("-fx-background-color: #404040; -fx-text-fill: white;"); // Gris
                    }
                }
            }
        });

        listaCanciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateBoton());

        listaCanciones.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Detectar doble clic
                try {
                    controlador.EventoGenerarRecomendaciones();
                } catch (LikedItemNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ComboBox "Recomendación"
        Label lRecomendacion = new Label("Recomendación:");
        lRecomendacion.setTextFill(Color.WHITE);
        lRecomendacion.setStyle("-fx-font-weight: bold;");

        cbRecomendacion = new ComboBox<>(FXCollections.observableArrayList("Género", "Similitudes"));
        cbRecomendacion.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-weight: bold; -fx-mark-color: #606060;");

        // ComboBox "Distancia"
        Label lDistancia = new Label("Distancia:");
        lDistancia.setTextFill(Color.WHITE);
        lDistancia.setStyle("-fx-font-weight: bold;");

        cbDistancia = new ComboBox<>(FXCollections.observableArrayList("Euclidea", "Manhattan"));
        cbDistancia.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-weight: bold; -fx-mark-color: #606060;");

        panelCentral.getChildren().addAll(titulo, listaCanciones, lRecomendacion, cbRecomendacion, lDistancia, cbDistancia);
        return panelCentral;
    }

    private VBox crearPanelDerecho() {
        VBox panelDerecho = new VBox(10);
        panelDerecho.setStyle("-fx-padding: 20;");

        Label titulo = new Label("Recomendaciones");
        titulo.setFont(new Font("Arial", 20)); // Tamaño de fuente aumentado
        titulo.setStyle("-fx-font-weight: bold; -fx-translate-y: -10;"); // Negrita y desplazamiento vertical
        titulo.setTextFill(Color.WHITE);

        // Label para mostrar la canción base seleccionada
        Label lCancionBase = new Label("Canción base: Ninguna seleccionada");
        lCancionBase.setTextFill(Color.WHITE);
        lCancionBase.setStyle("-fx-font-weight: bold; -fx-font-family: 'Roboto';");

        // Actualizar el texto del Label cuando se selecciona una canción
        listaCanciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lCancionBase.setText("Canción base: " + newValue);
            } else {
                lCancionBase.setText("Canción base: Ninguna seleccionada");
            }
        });

        listaRecomendaciones = new ListView<>();
        listaRecomendaciones.setStyle("-fx-background-color: #282828; -fx-text-fill: white;");
        listaRecomendaciones.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setStyle("-fx-background-color: #404040;"); // Fondo gris para celdas vacías
                } else {
                    setText(item);
                    setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-weight: bold;");
                }
            }
        });

        Label lNumRecs = new Label("Número de recomendaciones:");
        lNumRecs.setTextFill(Color.WHITE);
        lNumRecs.setStyle("-fx-font-weight: bold; -fx-font-family: 'Roboto';");

        numRecs = new TextField("5");
        numRecs.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-family: 'Roboto';");
        numRecs.setEditable(false); // Deshabilitar edición manual

        // Botones para ajustar el número de recomendaciones
        HBox botonesAjuste = new HBox(10);
        botonesAjuste.setAlignment(Pos.CENTER);

        Button btnMenos5 = new Button("-5");
        btnMenos5.setOnAction(e -> ajustarNumRecs(-5));

        Button btnMenos = new Button("-");
        btnMenos.setOnAction(e -> ajustarNumRecs(-1));

        Button btnMas = new Button("+");
        btnMas.setOnAction(e -> ajustarNumRecs(1));

        Button btnMas5 = new Button("+5");
        btnMas5.setOnAction(e -> ajustarNumRecs(5));

        btnMenos5.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-weight: bold;");
        btnMenos.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-weight: bold;");
        btnMas.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-weight: bold;");
        btnMas5.setStyle("-fx-background-color: #404040; -fx-text-fill: white; -fx-font-weight: bold;");

        botonesAjuste.getChildren().addAll(btnMenos5, btnMenos, btnMas, btnMas5);

        // Botón "Recomendar" centrado
        btnRecomendar = new Button("Recomendar");
        btnRecomendar.setDisable(true);
        btnRecomendar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #404040; -fx-text-fill: white;");
        btnRecomendar.setOnAction(e -> {
            try {
                controlador.EventoGenerarRecomendaciones();
            } catch (LikedItemNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox contenedorBoton = new HBox(btnRecomendar);
        contenedorBoton.setAlignment(Pos.CENTER); // Centrar el botón

        panelDerecho.getChildren().addAll(titulo, lCancionBase, listaRecomendaciones, lNumRecs, numRecs, botonesAjuste, contenedorBoton);
        return panelDerecho;
    }

    private void ajustarNumRecs(int ajuste) {
        int valorActual = Integer.parseInt(numRecs.getText());
        int nuevoValor = Math.max(1, valorActual + ajuste); // Asegurarse de que no sea menor que 1
        numRecs.setText(String.valueOf(nuevoValor));
        updateBoton();
    }

    private void updateBoton() {
        boolean ok = listaCanciones.getSelectionModel().getSelectedItem() != null
                && cbRecomendacion.getValue() != null
                && cbDistancia.getValue() != null
                && numRecs.getText().matches("\\d+");
        btnRecomendar.setDisable(!ok);
        if (ok) {
            btnRecomendar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #6739da; -fx-text-fill: white;");
        } else {
            btnRecomendar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #404040; -fx-text-fill: white;");
        }
    }

    @Override
    public void mostrarRecomendaciones(List<String> recomendaciones) {
        listaRecomendaciones.getItems().setAll(recomendaciones);
    }

    @Override
    public void habilitarBoton(boolean habilitar) {
        btnRecomendar.setDisable(!habilitar);
    }

    @Override
    public String getAlgoritmo() {
        String seleccion = cbRecomendacion.getValue();
        return switch (seleccion) {
            case "Género" -> "knn";
            case "Similitudes" -> "kmeans";
            default -> throw new IllegalArgumentException("Algoritmo no soportado: " + seleccion);
        };
    }

    @Override
    public String getDistancia() {
        return cbDistancia.getValue();
    }

    @Override
    public int getNumRecomendaciones() {
        return Integer.parseInt(numRecs.getText());
    }

    @Override
    public String getCancionSeleccionada() {
        return listaCanciones.getSelectionModel().getSelectedItem();
    }
}