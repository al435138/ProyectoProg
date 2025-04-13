package es.uji.al435138.machinelearning;

public class InvalidClusterNumberException extends Exception {
    private int numberOfClusters;

    public InvalidClusterNumberException(int numberOfClusters) {
        super("Número de clusters inválido: " + numberOfClusters);
        this.numberOfClusters = numberOfClusters;
    }

    public int getNumberOfClusters() {
        return numberOfClusters;
    }
}
