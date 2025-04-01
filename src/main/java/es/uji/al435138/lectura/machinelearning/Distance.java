package es.uji.al435138.lectura.machinelearning;

import java.util.List;

public interface Distance {
    double calculateDistance(List<Double> p, List<Double> q);
}
