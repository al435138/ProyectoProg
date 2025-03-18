package es.uji.al435138.lectura.table;

import java.util.HashMap;
import java.util.List;
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
        if (!labelsToIndex.containsKey(row.getLabel())) {
            labelsToIndex.put(row.getLabel(), labelsToIndex.size());
        }
    }

    public int getLabelAsInteger (String label) {
        return labelsToIndex.get(label);
    }

    @Override
    public int getRowCount() {
        return super.getRowCount();
    }

    @Override
    public List<Double> getColumnAt(int index) {
        return super.getColumnAt(index);
    }
}
