package es.uji.al435138.recommender.modelo;

import es.uji.al435138.csv.CSVLabeledFileReader;
import es.uji.al435138.machinelearning.*;
import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.RecSys;
import es.uji.al435138.table.TableWithLabels;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ImplementacionModelo implements Modelo {
    private final String songsFolder = "recommender/src/main/resources/songs/";
    private final String separator = System.getProperty("file.separator");

    public ImplementacionModelo() {}

    private Distance construirDistancia(String distancia) {
        return switch (distancia.toLowerCase()) {
            case "euclidean" -> new EuclideanDistance();
            case "manhattan" -> new ManhattanDistance();
            default -> throw new IllegalArgumentException("Distancia no soportada: " + distancia);
        };
    }

    @Override
    public List<String> cargarListaCanciones() throws IOException, URISyntaxException{
        String ruta = songsFolder + separator + "songs_train_names.txt";
        URL resource = getClass().getClassLoader().getResource(ruta);
        if (resource == null) {
            throw new URISyntaxException(ruta, "Fichero canciones no encontrado");
        }

        return Files.readAllLines(Paths.get(resource.toURI())).stream().map(String::trim).collect(Collectors.toList());
    }

    @Override
    public List<String> generarRecomendaciones(String algoritmo, String distancia, String cancionBase, int numRecs) throws Exception, LikedItemNotFoundException, LikedItemNotFoundException {
        List<String> canciones = cargarListaCanciones();
        CSVLabeledFileReader reader = new CSVLabeledFileReader(songsFolder + separator + "songs_train_names.txt");
        TableWithLabels tabla = (TableWithLabels) reader.readTableFromSource();

        Algorithm algoritmo = null;
        if (algoritmo.equals("knn")) {
            algoritmo = new KNN(construirDistancia(distancia));
        } else {
            algoritmo = new KMeans(15, 200, 4321, construirDistancia(distancia));
        }
        RecSys recsys = new RecSys(algoritmo);


        // Generar recomendaciones
        return recsys.recommend(cancionBase, numRecs);
    }
}
