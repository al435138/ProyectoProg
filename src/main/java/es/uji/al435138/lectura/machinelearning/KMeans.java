package es.uji.al435138.lectura.machinelearning;

import es.uji.al435138.lectura.table.Row;
import es.uji.al435138.lectura.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans implements Algorithm<Table, List<Double>, Integer> {
    private int numClusters;
    private int numIterations;
    private long seed;
    private List<List<Double>> centroids;
    private Distance distance;

    public KMeans(int numClusters, int numIterations, long seed, Distance distancia) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.centroids = new ArrayList<>();
        this.distance = distancia;
    }

    @Override
    public void train(Table data) throws InvalidClusterNumberException {
        if (numClusters <= 0 || numClusters > data.getRows().size()) {
            throw new InvalidClusterNumberException(numClusters);
        }

        Random random = new Random(seed);
        centroids.clear();
        List<Row> rows = data.getRows();
        for (int i = 0; i < numClusters; i++) {
            int index = random.nextInt(rows.size());
            centroids.add(rows.get(index).getData());
        }

        for (int iteracion = 0; iteracion < numIterations; iteracion++) {
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
                if (cluster.isEmpty()) continue;
                List<Double> newCentroid = new ArrayList<>();
                for (int j = 0; j < rows.get(0).getData().size(); j++) {
                    double sum = 0;
                    for (int index : cluster) {
                        sum += rows.get(index).getData().get(j);
                    }
                    newCentroid.add(sum / cluster.size());
                }
                centroids.set(i, newCentroid);
            }
        }
    }

    @Override
    public Integer estimate(List<Double> sample) {
        return getClosestCluster(sample);
    }

    private int getClosestCluster(List<Double> point) {
        double minDist = Double.MAX_VALUE;
        int closestCluster = 0;
        for (int i = 0; i < centroids.size(); i++) {
            double dist = calculateDistance(point, centroids.get(i));
            if (dist < minDist) {
                minDist = dist;
                closestCluster = i;
            }
        }
        return closestCluster;
    }

    private double calculateDistance(List<Double> a, List<Double> b) {
        double distancia = distance.calculateDistance(a, b);
        return distancia;
    }
}