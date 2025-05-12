package es.uji.al435138.recommender.controlador;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.modelo.Modelo;
import es.uji.al435138.recommender.vista.Vista;

import java.util.List;

public class ImplementacionControlador implements Controlador {
    private Modelo modelo;
    private Vista vista;

    @Override
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    @Override
    public void setVista(Vista vista) {
        this.vista = vista;
    }

    public void EventoGenerarRecomendaciones() throws LikedItemNotFoundException {
        String algoritmo = vista.getAlgoritmo();
        String distancia = vista.getDistancia();
        String cancionBase = vista.getCancionSeleccionada();
        int numRecs = vista.getNumRecomendaciones();

        try {
            List<String> recomendaciones = modelo.generarRecomendaciones(algoritmo, distancia, cancionBase, numRecs);
            vista.mostrarRecomendaciones(recomendaciones);
        } catch (Exception e) {
            System.out.println("Error al generar recomendaciones");
        }
    }
}