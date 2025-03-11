package es.uji.al435138.lectura;

import java.util.ArrayList;
import java.util.List;

public class Table {
    List <String> headers;
    public List <Row> rows;
    public Table () {
        this.rows = new ArrayList<>();
    }

    public Row getRowAt (int n) {
        return rows.get(n);
    }

    public List<Row> getRows() {
        return rows;
    }

    public void addRow (Row row) {
        rows.add(row);
    }

    public List <Double> getColumnAt (int index) {
        List <Double> column = new ArrayList<>();
        for (Row row : rows) {
            if (index < row.getData().size()) {
                column.add(row.getData().get(index));
            }
        }
        return column;
    }

    public List <String> getHeaders () {
        return headers;
    }

    public void setHeaders(List <String> headers) {
        this.headers = headers;
    }

    public int getRowCount () {
        return rows.size() -1;
    }

}
