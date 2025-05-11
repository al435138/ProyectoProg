package es.uji.al435138.recommender.modelo;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.RecSys;
import es.uji.al435138.recommender.vista.ImplementacionVista;

import es.uji.al435138.recommender.RecSys;
import es.uji.al435138.recommender.SongRecSys;
import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.vista.Vista;

import java.util.List;

public class ImplementacionModelo implements Modelo {
    private SongRecSys songRecSys;
    private String metodoSeleccionado;
    private String distanciaSeleccionada;
    private String cancionSeleccionada;
    private int numRecomendaciones;
    private Vista vista;
    private List<String> nombresCanciones;

    @Override
    public void inicializarSistema(String metodo) throws Exception, LikedItemNotFoundException {
        this.songRecSys = new SongRecSys(metodo);
        this.nombresCanciones = songRecSys.getAllNames();

        if (vista != null) {
            vista.mostrarListaCanciones(nombresCanciones); // método nuevo en la interfaz Vista
        }
    }

    @Override
    public List<String> obtenerTodasCanciones() {
        return nombresCanciones; // Devuelve la lista cargada
    }

    public ImplementacionModelo() {
        this.numRecomendaciones = 5;
    }

    @Override
    public void addVista(Vista vista) {
        this.vista = vista;
    }

    @Override
    public List<String> obtenerRecomendaciones() throws Exception, LikedItemNotFoundException {
        if (songRecSys == null) throw new IllegalStateException("Sistema no inicializado");
        if (cancionSeleccionada == null) throw new IllegalStateException("Canción no seleccionada");

        // Asumiendo que SongRecSys usa el tipo de distancia como parámetro adicional
        List<String> recomendaciones = songRecSys.recomendar(cancionSeleccionada, numRecomendaciones);
        return recomendaciones;
    }

    @Override
    public void setMetodoSeleccionado(String metodo) {
        this.metodoSeleccionado = metodo;
    }

    @Override
    public void setDistanciaSeleccionada(String distancia) {
        this.distanciaSeleccionada = distancia;
    }

    @Override
    public void setCancionSeleccionada(String cancion) {
        this.cancionSeleccionada = cancion;
    }

    @Override
    public void setNumRecomendaciones(int num) {
        this.numRecomendaciones = num;
    }

    @Override
    public String getMetodoSeleccionado() {
        return metodoSeleccionado;
    }

    @Override
    public String getDistanciaSeleccionada() {
        return distanciaSeleccionada;
    }

    @Override
    public String getCancionSeleccionada() {
        return cancionSeleccionada;
    }

    @Override
    public int getNumRecomendaciones() {
        return numRecomendaciones;
    }
}
