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

    @Override
    public void metodoSeleccionadoCambiado(String metodo) throws LikedItemNotFoundException {
        try {
            modelo.setMetodoSeleccionado(metodo); // <--- añadido
            modelo.inicializarSistema(metodo.toLowerCase());
            vista.mostrarCanciones(modelo.obtenerTodasCanciones());
        } catch (Exception e) {
            vista.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }

    @Override
    public void distanciaSeleccionadaCambiada(String distancia) {
        modelo.setDistanciaSeleccionada(distancia);
    }

    @Override
    public void cancionSeleccionadaCambiada() {
        String seleccion = vista.getCancionSeleccionada();
        if (seleccion != null) {
            modelo.setCancionSeleccionada(seleccion);
        }
    }

    @Override
    public void setNumRecomendaciones(int num) {
        modelo.setNumRecomendaciones(num);
    }

    @Override
    public void generarRecomendaciones() {
        try {
            // Verificar que hay una canción seleccionada
            if(modelo.getCancionSeleccionada() == null || modelo.getCancionSeleccionada().isEmpty()) {
                vista.mostrarError("No se ha seleccionado ninguna canción");
                return;
            }

            // Obtener número de recomendaciones
            int numRec = modelo.getNumRecomendaciones();
            if(numRec <= 0) {
                vista.mostrarError("Número de recomendaciones no válido");
                return;
            }

            // Generar recomendaciones
            List<String> recomendaciones = modelo.obtenerRecomendaciones();
            vista.mostrarRecomendaciones(recomendaciones);

        } catch (Exception e) {
            vista.mostrarError("Error al generar recomendaciones: " + e.getMessage());
        } catch (LikedItemNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}