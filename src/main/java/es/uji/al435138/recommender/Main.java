package es.uji.al435138.recommender;

import es.uji.al435138.recommender.controlador.ImplementacionControlador;
import es.uji.al435138.recommender.modelo.ImplementacionModelo;
import es.uji.al435138.recommender.vista.ImplementacionVista;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage stage) throws Exception{
        // Crear el modelo
        ImplementacionModelo modelo = new ImplementacionModelo();

        // Crear la vista
        ImplementacionVista vista = new ImplementacionVista(stage);

        // Crear el controlador y establecer el modelo y la vista
        ImplementacionControlador controlador = new ImplementacionControlador();
        controlador.setModelo(modelo);
        controlador.setVista(vista);

        // Establecer el controlador en la vista
        modelo.setVista(vista);
        vista.setModelo(modelo);
        vista.setControlador(controlador);
        vista.creaGUI();
    }
}