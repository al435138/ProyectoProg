package es.uji.al435138.lectura.machinelearning;

import es.uji.al435138.lectura.table.Row;
import es.uji.al435138.lectura.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
    private int numClusters;
    private int numIterations;
    private long seed;
    private List<List<Double>> centroids;

    public double calcularDistancia(List<Double> a, List<Double> b){
        double suma = 0;
        for (int i = 0; i < a.size(); i++) {
            suma += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(suma);
    }

    private int getClosestCluster(List<Double> point){
        double minDist = Double.MAX_VALUE;
        int closestCluster = 0;
        for (int i = 0; i < centroids.size(); i++) {
            double dist = calcularDistancia(point, centroids.get(i));
            if (dist < minDist){
                minDist = dist;
                closestCluster = i;
            }
        }
        return closestCluster;
    }

    public List<List<Double>> getCentroids() {
        return centroids;
    }

    public KMeans(int numClusters, int numIterations, long seed){
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
    }

    public void train(Table data){
        Random random = new Random(seed);

        //Elegir centroides aleatorios
        centroids.clear();
        List<Row> rows = data.getRows();
        for (int i = 0; i < numClusters; i++) {
            int index = random.nextInt(rows.size());
            centroids.add(rows.get(index).getData());
        }

        //Asignar cada dato a un grupo
        for (int iteracion = 0; iteracion < numIterations; iteracion++){
            List<List<Integer>> clusters = new ArrayList<>();
            for (int i = 0; i < numClusters; i++) {
                clusters.add(new ArrayList<>());
            }
            for (int i = 0; i < rows.size(); i++) {
                List<Double> rowData = rows.get(i).getData();
                int closestCluster = getClosestCluster(rowData);
                clusters.get(closestCluster).add(i);
            }

            for (int i = 0; i < numClusters; i++) {
                List<Integer> cluster = clusters.get(i);
                if (cluster.isEmpty()) continue; // Evitar divisiones por cero si un cluster no tiene puntos

                // Calculamos el nuevo centroide como el promedio de los puntos del cluster
                List<Double> newCentroid = new ArrayList<>();
                for (int j = 0; j < rows.get(0).getData().size(); j++) { // Iterar sobre las columnas
                    double sum = 0;
                    for (int index : cluster) {
                        sum += rows.get(index).getData().get(j);
                    }
                    newCentroid.add(sum / cluster.size());
                }
                centroids.set(i, newCentroid); // Actualizar el centroide
            }


        }

    }

    public Integer estimate(List<Double> sample){
        return 0;
    }
}
