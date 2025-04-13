package es.uji.al435138.machinelearning;

import java.util.List;

public class ManhattanDistance implements Distance {
    @Override
    public double calculateDistance(List<Double> p, List<Double> q) {
        double suma = 0;
        for (int i = 0; i < p.size(); i++) {
            suma += Math.abs(p.get(i) - q.get(i));
        }
        return suma;
    }
}
