package es.uji.al435138.mvc;

import es.uji.al435138.recommender.SongRecSys;
import es.uji.al435138.recommender.LikedItemNotFoundException;

import java.util.List;

public class Modelo {
    private SongRecSys songRecSys;

    public void cargarRecomendador(String metodo) throws Exception, LikedItemNotFoundException {
        this.songRecSys = new SongRecSys(metodo); // "knn" o "kmeans"
    }

    public List<String> obtenerCanciones() {
        return songRecSys.getAllNames();
    }

    public List<String> recomendar(String cancion, int cantidad) throws LikedItemNotFoundException {
        return songRecSys.recomendar(cancion, cantidad);
    }
}
