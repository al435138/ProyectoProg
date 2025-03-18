package es.uji.al435138.lectura.machinelearning;
import es.uji.al435138.lectura.table.Row;
import es.uji.al435138.lectura.table.RowWithLabel;
import es.uji.al435138.lectura.table.TableWithLabels;

import java.util.List;

public class KNN {
    private TableWithLabels data;

    public void train(TableWithLabels data) {
        this.data = data;
    }

    public Integer estimate(List<Double> sample) {
        double minDist = Double.MAX_VALUE;
        int closestLabel = -1;

        for (Row row : data.getRows()) {
            RowWithLabel labelRow = (RowWithLabel) row;
            double distance = euclideanDistance(sample, labelRow.getData());

            if (distance < minDist) {
                minDist = distance;
                closestLabel = Integer.parseInt(labelRow.getLabel());
            }
        }

        return closestLabel;
    }

    private Double euclideanDistance(List<Double> a, List<Double> b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("Las listas deben tener el mismo tamaño");
        }
        double suma = 0;
        for (int i = 0; i < a.size(); i++) {
            suma += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(suma);
    }
}
