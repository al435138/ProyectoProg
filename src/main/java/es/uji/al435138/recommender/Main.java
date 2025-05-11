package es.uji.al435138.recommender;

import es.uji.al435138.recommender.controlador.ImplementacionControlador;
import es.uji.al435138.recommender.modelo.ImplementacionModelo;
import es.uji.al435138.recommender.vista.ImplementacionVista;
import javafx.application.Application;

public class Main {

    public class AppContext {
        public static ImplementacionModelo modelo;
        public static ImplementacionControlador controlador;
    }

    public static void main(String[] args) {
        // Crear modelo y controlador
        AppContext.modelo = new ImplementacionModelo();
        AppContext.controlador = new ImplementacionControlador();

        // Iniciar aplicación JavaFX
        Application.launch(ImplementacionVista.class, args);
    }
}