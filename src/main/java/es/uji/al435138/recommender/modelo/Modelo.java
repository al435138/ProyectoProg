package es.uji.al435138.recommender.modelo;

import es.uji.al435138.recommender.LikedItemNotFoundException;

import java.util.List;

public interface Modelo {
    List<String> cargarListaCanciones() throws Exception, LikedItemNotFoundException;
    List<String> generarRecomendaciones(String algoritmo, String distancia, String cancionBase, int numRecs) throws Exception, LikedItemNotFoundException;
}
