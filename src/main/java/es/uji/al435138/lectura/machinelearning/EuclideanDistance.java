package es.uji.al435138.lectura.machinelearning;

import java.util.List;

public class EuclideanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double suma = 0;
        for (int i = 0; i < p.size(); i++) {
            suma += Math.pow(p.get(i) - q.get(i), 2);
        }
        return Math.sqrt(suma);
    }
}
