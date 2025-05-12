package es.uji.al435138.recommender.vista;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.controlador.Controlador;
import es.uji.al435138.recommender.modelo.Modelo;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class ImplementacionVista implements Vista {
    private Controlador controlador;
    private final Stage stage;
    private Modelo modelo;

    // Componentes principales
    private ComboBox<String> cbRecomendacion;
    private ComboBox<String> cbDistancia;
    private ListView<String> listaCanciones;
    private ListView<String> listaRecomendaciones;
    private Button btnRecomendar;
    private VBox izqda;
    private VBox drcha;
    private VBox centro;
    private TextField numRecs;

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
        creaGUIIzquierda();
        izqda.setAlignment(Pos.TOP_LEFT);

        try {
            creaGUICentro();
        } catch (LikedItemNotFoundException e) {
            throw new RuntimeException(e);
        }
        centro.setAlignment(Pos.TOP_CENTER);

        creaGUIDerecha();
        drcha.setAlignment(Pos.TOP_RIGHT);

        HBox root = new HBox(20, izqda, centro, drcha);
        Scene escena = new Scene(root, 900, 500);
        stage.setScene(escena);
        stage.show();
    }

    public void creaGUIIzquierda(){
        Label l1 = new Label("Recomendacion");
        cbRecomendacion = new ComboBox<>(FXCollections.observableArrayList("Género", "Similitudes"));
        Label l2 = new Label("Distancia");
        cbDistancia = new ComboBox<>(FXCollections.observableArrayList("Euclidea", "Mahattan"));
        Label l3 = new Label("Número de recomendaciones");
        numRecs = new TextField("5");

        btnRecomendar = new Button("Recomendar");
        btnRecomendar.setDisable(true);
        btnRecomendar.setOnAction(e -> {
            try {
                controlador.EventoGenerarRecomendaciones();
            } catch (LikedItemNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        izqda = new VBox(10, l1, cbRecomendacion, l2, cbDistancia, l3, numRecs, btnRecomendar);
    }

    public void creaGUICentro() throws LikedItemNotFoundException, Exception {
        Label l4 = new Label("Canciones disponibles");

        List<String> canciones = modelo.cargarListaCanciones();

        listaCanciones = new ListView<>(FXCollections.observableArrayList(canciones));

        listaCanciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Canción seleccionada" + newValue);
            updateBoton();
        });

        centro = new VBox(10, l4, listaCanciones);
    }

    public void creaGUIDerecha(){
        Label l5 = new Label("Recomendaciones");

        listaRecomendaciones = new ListView<>();

        drcha = new VBox(10, l5, listaRecomendaciones);
    }

    private void updateBoton() {
        boolean ok = listaCanciones.getSelectionModel().getSelectedItem() != null
                && cbRecomendacion.getValue() != null
                && cbDistancia.getValue() != null
                && numRecs.getText().matches("\\d+");
        btnRecomendar.setDisable(!ok);
        habilitarBoton(ok);
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
        return cbRecomendacion.getValue();
    }

    @Override
    public String getDistancia() {
        return cbDistancia.getValue();
    }

    @Override
    public int getNumRecomendaciones(){
        return Integer.parseInt(numRecs.getText());
    }

    public String getCancionSeleccionada() {
        return listaCanciones.getSelectionModel().getSelectedItem();
    }
}