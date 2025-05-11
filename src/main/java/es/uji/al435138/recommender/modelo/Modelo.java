package es.uji.al435138.recommender.modelo;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.vista.Vista;

import java.util.List;

public interface Modelo {
    void inicializarSistema(String metodo) throws Exception, LikedItemNotFoundException;
    List<String> obtenerTodasCanciones();
    List<String> obtenerRecomendaciones() throws Exception, LikedItemNotFoundException;
    void setMetodoSeleccionado(String metodo);
    void setDistanciaSeleccionada(String distancia);
    void setCancionSeleccionada(String cancion);
    void setNumRecomendaciones(int num);
    void addVista(Vista vista);
    String getMetodoSeleccionado();
    String getDistanciaSeleccionada();
    String getCancionSeleccionada();
    int getNumRecomendaciones();
}
