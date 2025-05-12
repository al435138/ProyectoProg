package es.uji.al435138.recommender.modelo;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.vista.Vista;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface Modelo {
    List<String> cargarListaCanciones() throws IOException, URISyntaxException;
    List<String> generarRecomendaciones(String algoritmo, String distancia, String cancionBase, int numRecs) throws Exception, LikedItemNotFoundException;
}
