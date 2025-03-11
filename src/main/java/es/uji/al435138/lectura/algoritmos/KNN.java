package es.uji.al435138.lectura.algoritmos;
import es.uji.al435138.lectura.rows.Row;
import es.uji.al435138.lectura.rows.RowWithLabel;
import es.uji.al435138.lectura.tables.TableWithLabels;

import java.util.List;

public class KNN {
    private TableWithLabels data;

    public void train(TableWithLabels data) {
        this.data = data;
    }

    public Integer estimate(List<Double> inputData) {
        Double minDist = Double.MAX_VALUE;
        int closestLabel = -1;

        for (Row row : data.getRows()) {
            RowWithLabel labelRow = (RowWithLabel) row;
            Double distance = euclideanDistance(inputData, labelRow.getData());

            if (distance < minDist) {
                minDist = distance;
                closestLabel = Integer.parseInt(labelRow.getLabel());
            }
        }
        return closestLabel;
    }

    private Double euclideanDistance(List<Double> a, List<Double> b) {
        double suma = 0;
        for (int i = 0; i < a.size(); i++) {
            suma += Math.pow(a.get(i) - b.get(i), 2);
        }
        return Math.sqrt(suma);
    }
}
