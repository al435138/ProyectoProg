package es.uji.al435138.lectura.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.uji.al435138.lectura.table.Row;
import es.uji.al435138.lectura.table.RowWithLabel;
import es.uji.al435138.lectura.table.Table;
import es.uji.al435138.lectura.table.TableWithLabels;


public class CSV {
    public Table readTable (String nFich) throws IOException {
        Table table = new Table();
        List<String> columnTitles = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(nFich))){
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(firstLine) {
                    for (String value : values) {
                        columnTitles.add(value.trim());
                    }
                    table.setHeaders(new ArrayList<>(columnTitles));
                    firstLine = false;
                }
                List<Double> rowData = new ArrayList<>();
                for (int i = 0; i < values.length-1; i++) {
                    String value = values[i];
                    rowData.add(Double.parseDouble(value.trim()));
                }
                table.addRow(new Row(rowData));
            }
        }
        return table;
    }

    public TableWithLabels readTableWithLabels (String nFich) throws IOException {
        TableWithLabels table = new TableWithLabels();
        List<String> columnTitles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nFich))) {
            String line;
            boolean firstLine = true;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                if (firstLine) {
                    for (String value : values) {
                        columnTitles.add(value.trim());
                    }
                    table.setHeaders(new ArrayList<>(columnTitles));
                    firstLine = false;
                } else{
                    List<Double> rowData = new ArrayList<>();
                    for (int i = 0; i < values.length - 1; i++) {
                        String value = values[i];
                        rowData.add(Double.parseDouble(value.trim()));
                    }
                    String label = values[values.length - 1];
                    table.addRow(new RowWithLabel(rowData, label));
                }
            }
        }
        return table;
    }
}

