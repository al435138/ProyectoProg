package es.uji.al435138.recommender.controlador;

import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.modelo.Modelo;
import es.uji.al435138.recommender.vista.Vista;

public interface Controlador {
    void setModelo(Modelo modelo);
    void setVista(Vista vista);
    void EventoGenerarRecomendaciones() throws LikedItemNotFoundException;
}
