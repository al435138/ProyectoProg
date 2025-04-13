package es.uji.al435138.lectura.csv;

import es.uji.al435138.lectura.table.Table;
import es.uji.al435138.lectura.table.Row;

import java.util.ArrayList;
import java.util.List;

public class CSVUnlabeledFileReader extends FileReader<Table> {

    public CSVUnlabeledFileReader(String filename) {
        super(filename);
    }

    protected void createTable() {
        table = new Table();
    }

    @Override
    protected void processHeaders(String headers) {
        String[] values = headers.split(",");
        List<String> columnTitles = new ArrayList<>();
        for (String value : values) {
            columnTitles.add(value.trim());
        }
        table.setHeaders(columnTitles);
    }

    @Override
    protected void processData(String data) {
        String[] values = data.split(",");
        List<Double> rowData = new ArrayList<>();
        for (String value : values) {
            try {
                rowData.add(Double.parseDouble(value.trim()));
            } catch (NumberFormatException e) {
                System.err.println("Error al convertir a número: " + value);
            }
        }
        table.addRow(new Row(rowData));
    }
}
