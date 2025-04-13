package es.uji.al435138.machinelearning;
import es.uji.al435138.table.Row;
import es.uji.al435138.table.RowWithLabel;
import es.uji.al435138.table.TableWithLabels;

import java.util.List;

public class KNN implements Algorithm<TableWithLabels, List<Double>, Integer> {
    private TableWithLabels data;
    private Distance distance;

    public KNN() {}

    public KNN(Distance distance) {
        this.distance = distance;
    }

    @Override
    public void train(TableWithLabels data) {
        this.data = data;
    }

    @Override
    public Integer estimate(List<Double> sample) {
        double minDist = Double.MAX_VALUE;
        int closestLabel = -1;
        for (Row row : data.getRows()) {
            RowWithLabel labelRow = (RowWithLabel) row;
            double distance = calculateDistance(sample, labelRow.getData());
            if (distance < minDist) {
                minDist = distance;
                closestLabel = data.getLabelAsInteger(labelRow.getLabel());
            }
        }
        return closestLabel;
    }

    private Double calculateDistance(List<Double> a, List<Double> b) {
        double distancia = distance.calculateDistance(a, b);
        return distancia;
    }
}

