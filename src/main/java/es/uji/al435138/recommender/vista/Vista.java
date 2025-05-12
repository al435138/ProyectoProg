package es.uji.al435138.recommender.vista;

import java.util.List;

public interface Vista {
    String getAlgoritmo();
    String getDistancia();
    int getNumRecomendaciones();
    String getCancionSeleccionada();
    void mostrarRecomendaciones(List<String> recomendaciones);
    void habilitarBoton(boolean habilitar);
}
