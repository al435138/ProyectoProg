package es.uji.al435138.table;

import java.util.List;

public class Row {
    private List<Double> data;

    public Row (List<Double> data) {
        this.data = data;
        }
    public List<Double> getData() {
        return this.data;

    }

}
