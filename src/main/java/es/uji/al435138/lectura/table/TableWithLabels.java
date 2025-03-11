package es.uji.al435138.lectura.table;

import java.util.HashMap;
import java.util.Map;

public class TableWithLabels extends Table {
    private Map<String, Integer> labelsToIndex;

    public TableWithLabels() {
        super();
        this.labelsToIndex = new HashMap<>();

    }
    @Override
    public RowWithLabel getRowAt (int n) {
        return (RowWithLabel) super.getRows().get(n);
    }

    @Override
    public void addRow(RowWithLabel row) {
        super.addRow(row);
    }

    public int getLabelAsInteger (String label) {
        return labelsToIndex.get(label);
    }

}
