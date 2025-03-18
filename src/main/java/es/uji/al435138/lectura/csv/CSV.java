package es.uji.al435138.lectura.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import es.uji.al435138.lectura.table.Row;
import es.uji.al435138.lectura.table.RowWithLabel;
import es.uji.al435138.lectura.table.Table;
import es.uji.al435138.lectura.table.TableWithLabels;


public class CSV {
    public Table readTable(String nFich) throws IOException, URISyntaxException {
        Table table = new Table();
        String file = getClass().getClassLoader().getResource(nFich).toURI().getPath();
        List<String> columnTitles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (firstLine) {
                    // Guardamos los títulos de las columnas
                    for (String value : values) {
                        columnTitles.add(value.trim());
                    }
                    table.setHeaders(new ArrayList<>(columnTitles));
                    firstLine = false;
                    continue;
                }

                List<Double> rowData = new ArrayList<>();
                try {
                    for (int i = 0; i < values.length; i++) {
                        rowData.add(Double.parseDouble(values[i].trim()));
                    }
                    table.addRow(new Row(rowData));
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir a número");
                }
            }
        }
        return table;
    }

    public TableWithLabels readTableWithLabels(String nFich) throws IOException, URISyntaxException {
        TableWithLabels table = new TableWithLabels();
        String file = getClass().getClassLoader().getResource(nFich).toURI().getPath();
        List<String> columnTitles = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (firstLine) {
                    // Añadir todas las columnas excepto la última (la etiqueta)
                    for (int i = 0; i < values.length - 1; i++) {
                        columnTitles.add(values[i].trim());
                    }
                    table.setHeaders(new ArrayList<>(columnTitles));
                    firstLine = false;
                    continue;
                }

                List<Double> rowData = new ArrayList<>();
                for (int i = 0; i < values.length - 1; i++) { // Solo datos numéricos
                    rowData.add(Double.parseDouble(values[i].trim()));
                }
                String label = values[values.length - 1].trim(); // Última columna = etiqueta
                table.addRow(new RowWithLabel(rowData, label));
            }
        }
        return table;
    }
}

