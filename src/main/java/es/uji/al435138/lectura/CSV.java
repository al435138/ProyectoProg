package es.uji.al435138.lectura;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import es.uji.al435138.lectura.Table;
import es.uji.al435138.lectura.TableWithLabels;


public class CSV {
    public Table readTable (String nFich) throws URISyntaxException {
        Table tabla = new Table ();
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
        TableWithLabels tabla = new TableWithLabels ();
        Scanner scanner = new Scanner(getClass().getClassLoader().getResource(nFich).toURI().getPath());
        String line;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String [] values = line.split(",");
            List <Double> rowData = new ArrayList<>();
            for (int i = 0; i < values.length -1; i++) {
                rowData.add(Double.parseDouble(values[i]));
            }
            String label = values[values.length -1];
            tabla.addRow(new RowWithLabel(rowData, label));
        }


        return tabla;
    }
}

