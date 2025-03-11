package es.uji.al435138.lectura;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.uji.al435138.lectura.RowWithLabel;

public class TableWithLabels extends Table {
    public Map<String, Integer> labelsToIndex;
    public int contador = 0;

    public TableWithLabels() {
        super();
        this.labelsToIndex = new HashMap<>();

    }

    public List<Row> getRows() {
        return rows;
    }

    @Override
    public void addRow(Row row) {
        if (row instanceof RowWithLabel) {
            super.addRow(row);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addRowWithLabel (RowWithLabel row, String label) {
        if(!labelsToIndex.containsKey(label)) {
            labelsToIndex.put(label, contador++);
        }
        super.addRow(row);
    }

    public int getLabelAsInteger (String label) {
        return labelsToIndex.get(label);
    }

}
