package es.uji.al435138.recommender.vista;

import es.uji.al435138.recommender.controlador.Controlador;
import es.uji.al435138.recommender.modelo.Modelo;

import java.util.List;

public interface Vista {
    void setControlador(Controlador controlador);
    void setModelo(Modelo modelo);
    void mostrarCanciones(List<String> canciones);
    void mostrarRecomendaciones(List<String> recomendaciones);
    void mostrarError(String mensaje);
    String getCancionSeleccionada();
    void actualizarEstadoBoton(boolean estado);
    void mostrarListaCanciones(List<String> nombresCanciones);
}
