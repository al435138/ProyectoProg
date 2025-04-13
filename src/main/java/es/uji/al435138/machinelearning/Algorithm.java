package es.uji.al435138.machinelearning;

import es.uji.al435138.table.Table;

public interface Algorithm<T extends Table, U, V> {
    void train(T data) throws InvalidClusterNumberException;
    V estimate(U sample);
}
