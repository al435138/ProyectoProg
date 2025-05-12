package es.uji.al435138.recommender.modelo;

import es.uji.al435138.csv.CSV;
import es.uji.al435138.csv.CSVLabeledFileReader;
import es.uji.al435138.machinelearning.*;
import es.uji.al435138.recommender.LikedItemNotFoundException;
import es.uji.al435138.recommender.RecSys;
import es.uji.al435138.recommender.vista.Vista;
import es.uji.al435138.table.Table;
import es.uji.al435138.table.TableWithLabels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImplementacionModelo implements Modelo {
    private final String songsFolder = "recommender";
    private final String separator = System.getProperty("file.separator");
    private Vista vista;

    public ImplementacionModelo() {}

    @Override
    public void setVista(Vista vista) {
        this.vista = vista;
    }

    private Distance construirDistancia(String distancia) {
        return switch (distancia.toLowerCase()) {
            case "euclidea" -> new EuclideanDistance();
            case "manhattan" -> new ManhattanDistance();
            default -> throw new IllegalArgumentException("Distancia no soportada: " + distancia);
        };
    }

    @Override
    public List<String> cargarListaCanciones() throws URISyntaxException, IOException {
        String ruta = "recommender/songs_train_names.csv";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ruta);

        if (inputStream == null) {
            throw new IOException("Fichero canciones no encontrado en el classpath: " + ruta);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().map(String::trim).collect(Collectors.toList());
        }
    }

    @Override
    public List<String> generarRecomendaciones(String algoritmo, String distancia, String cancionBase, int numRecs) throws Exception, LikedItemNotFoundException {
        String sep = System.getProperty("file.separator");
        String ruta = "recommender";

        // Mapear archivos según el algoritmo
        Map<String, String> archivosTrain = Map.of(
                "knn", ruta + sep + "songs_train.csv",
                "kmeans", ruta + sep + "songs_train_withoutnames.csv"
        );
        Map<String, String> archivosTest = Map.of(
                "knn", ruta + sep + "songs_test.csv",
                "kmeans", ruta + sep + "songs_test_withoutnames.csv"
        );

        // Seleccionar el algoritmo
        Distance distanciaSeleccionada = construirDistancia(distancia);
        Algorithm<?, List<Double>, Integer> algoritmoSeleccionado = switch (algoritmo.toLowerCase()) {
            case "knn" -> new KNN(distanciaSeleccionada);
            case "kmeans" -> new KMeans(15, 200, 4321, distanciaSeleccionada);
            default -> throw new IllegalArgumentException("Algoritmo no soportado: " + algoritmo);
        };

        // Leer los datos de entrenamiento y prueba
        CSV csv = new CSV();
        Table trainData = csv.readTableWithLabels(archivosTrain.get(algoritmo.toLowerCase()));
        Table testData = csv.readTableWithLabels(archivosTest.get(algoritmo.toLowerCase()));

        // Leer los nombres de las canciones de prueba
        List<String> testNames = cargarListaCanciones();

        // Validar que los tamaños coincidan
        if (testNames.size() != testData.getRowCount()) {
            throw new IllegalArgumentException("El número de nombres de canciones no coincide con el número de filas en los datos de prueba.");
        }

        // Inicializar y entrenar el sistema de recomendación
        RecSys<Table, List<Double>, Integer> recSys = new RecSys<>(algoritmoSeleccionado);
        recSys.train(trainData);
        recSys.initialise(testData, testNames);

        // Generar recomendaciones
        return recSys.recommend(cancionBase, numRecs);
    }
}
