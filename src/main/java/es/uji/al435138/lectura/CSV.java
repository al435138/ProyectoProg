package es.uji.al435138.lectura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.uji.al435138.lectura.rows.Row;
import es.uji.al435138.lectura.tables.Table;
import es.uji.al435138.lectura.tables.TableWithLabels;


public class CSV {
    public Table readTable (String nFich) throws URISyntaxException {
        Table tabla = new Table();
        Scanner scanner = new Scanner(getClass().getClassLoader().getResource(nFich).toURI().getPath());
        String line;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String [] values = line.split(",");
            List <Double> rowData = new ArrayList<>();
            for (String value : values) {
                rowData.add(Double.parseDouble(value));
            }
            tabla.addRow(new Row(rowData));
        }


        return tabla;
    }
    public Table readTableWithLabels (String nFich) throws URISyntaxException {
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
                    table.addRow(new ArrayList<>(rowData), label);
                }
            }
        }
        return table;
    }
}

