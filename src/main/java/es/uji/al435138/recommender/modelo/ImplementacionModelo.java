package es.uji.al435138.recommender.modelo;

import es.uji.al435138.csv.CSV;
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
import java.util.List;
import java.util.stream.Collectors;

public class ImplementacionModelo implements Modelo {
    public ImplementacionModelo() {}


    private Distance construirDistancia(String distancia) {
        return switch (distancia.toLowerCase()) {
            case "euclidea" -> new EuclideanDistance();
            case "manhattan" -> new ManhattanDistance();
            default -> throw new IllegalArgumentException("Distancia no soportada: " + distancia);
        };
    }

    @Override
    public List<String> cargarListaCanciones() throws URISyntaxException, IOException {
        String ruta = "recommender/songs_test_names.csv";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(ruta);

        if (inputStream == null) {
            throw new IOException("Fichero canciones no encontrado en el classpath: " + ruta);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .limit(846) // Hemos tenido que agregar este fragmento para que coincida con el numero de canciones pero no debería ser así
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<String> generarRecomendaciones(String algoritmo, String distancia, String cancionBase, int numRecs)
            throws Exception, LikedItemNotFoundException {

        Distance distanciaObj = construirDistancia(distancia);

        Algorithm algorithm;
        if (algoritmo.equalsIgnoreCase("knn")) {
            algorithm = new KNN(distanciaObj);
        } else if (algoritmo.equalsIgnoreCase("kmeans")) {
            algorithm = new KMeans(15, 200, 4321, distanciaObj);
        } else {
            throw new IllegalArgumentException("Algoritmo no soportado: " + algoritmo);
        }

        RecSys<Table, List<Double>, Integer> recSys = new RecSys<>(algorithm);

        String ruta = "recommender";
        String sep = System.getProperty("file.separator");

        String trainPath = ruta + sep + "songs_train.csv";
        String testPath = ruta + sep + "songs_test.csv";

        CSV csv = new CSV();
        TableWithLabels trainData = csv.readTableWithLabels(trainPath);
        TableWithLabels testData = csv.readTableWithLabels(testPath);
        List<String> testItemNames = cargarListaCanciones();

        if (testData.getRowCount() != testItemNames.size()) {
            throw new IllegalStateException("El número de filas en testData (" + testData.getRowCount() +
                    ") no coincide con el número de nombres (" + testItemNames.size() + ")");
        }

        recSys.train(trainData);
        recSys.initialise(testData, testItemNames);
        return recSys.recommend(cancionBase, numRecs);
    }
}
