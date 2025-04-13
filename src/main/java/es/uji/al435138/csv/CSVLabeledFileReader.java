package es.uji.al435138.csv;

import es.uji.al435138.table.RowWithLabel;
import es.uji.al435138.table.TableWithLabels;

import java.util.ArrayList;
import java.util.List;

public class CSVLabeledFileReader extends FileReader<TableWithLabels> {

    public CSVLabeledFileReader(String filename) {
        super(filename);
    }

    @Override
    protected void createTable() {
        table = new TableWithLabels();
    }

    @Override
    protected void processHeaders(String headers) {
        String[] values = headers.split(",");
        List<String> columnTitles = new ArrayList<>();
        for (int i = 0; i < values.length - 1; i++) {
            columnTitles.add(values[i].trim());
        }
        table.setHeaders(columnTitles);
    }

    @Override
    protected void processData(String data) {
        String[] values = data.split(",");
        List<Double> rowData = new ArrayList<>();
        for (int i = 0; i < values.length - 1; i++) {
            try {
                rowData.add(Double.parseDouble(values[i].trim()));
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir a número: " + values[i]);
            }
        }
        String label = values[values.length - 1].trim();
        table.addRow(new RowWithLabel(rowData, label));
    }
}
