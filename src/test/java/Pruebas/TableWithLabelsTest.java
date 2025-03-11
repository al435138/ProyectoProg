// TODO: Reemplazar por el nombre de tu paquete
package Pruebas;

// TODO: Reemplazar por los imports de tu proyecto
import es.uji.al435138.lectura.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableWithLabelsTest {

    private TableWithLabels tableWithLabels;

    private int amountOfColumns;
    private List<String> headers;
    private List<String> labels;
    private int amountOfRows =  6;

    public TableWithLabelsTest() {
        this.headers = List.of("header1", "header2", "header3", "header4", "headers5");
        this.labels = List.of("label1", "label2", "label3");
        this.amountOfColumns = headers.size();
    }

    @BeforeEach
    void setUp() throws IOException {
        List<RowWithLabel> rows = generateData(amountOfRows, amountOfColumns);
        // Hay dos opciones para crear las tablas:
        // (1) Añadiendo la cabecera y luego fila a fila con funciones para ello, como "setHeaders" y  "addRow".
        // (2) Inyectando la cabecera y las filas mediante el constructor.
        // TODO: Descomenta/comenta la opcion que uses/no uses.


        //---(1) Setter y addRow---
        tableWithLabels = new TableWithLabels();
        tableWithLabels.setHeaders(headers);
        rows.forEach(row -> tableWithLabels.addRow(row));
        //-------------------------

        //---(2) Inyeccion por constructor---
        //tableWithLabels = new TableWithLabels(headers, rows);
        //-----------------------------------
    }

    @AfterEach
    void tearDown() {
        tableWithLabels = null;
    }


    @Test
    @DisplayName("TableWithLabels - getHeaders")
    void getHeaders() {
        // assert if the headers are correctly read
        assertEquals(List.of("header1", "header2", "header3", "header4", "headers5"), tableWithLabels.getHeaders());
    }

    @Test
    @DisplayName("TableWithLabels - getRowAt")
    void getRowAt() {
        // assert that the first and last row are correctly read
        assertEquals(List.of(0.0, 1.0, 2.0, 3.0, 4.0), tableWithLabels.getRowAt(0).getData());
        assertEquals("label1", tableWithLabels.getRowAt(0).getLabel());
        assertEquals(List.of(25.0, 26.0, 27.0, 28.0, 29.0), tableWithLabels.getRowAt(this.amountOfRows - 1).getData());
        assertEquals("label3", tableWithLabels.getRowAt(this.amountOfRows - 1).getLabel());

        // assert that an arbitrary row is correctly read
        assertEquals(List.of(10.0, 11.0, 12.0, 13.0, 14.0), tableWithLabels.getRowAt(2).getData());
        assertEquals("label3", tableWithLabels.getRowAt(2).getLabel());
        // assert that the right amount of columns is read
        assertEquals(this.amountOfColumns, tableWithLabels.getRowAt(3).getData().size());
    }

    @Test
    @DisplayName("TableWithLabels - rowCount")
    void rowCount() {
        assertEquals(amountOfRows, tableWithLabels.getRowCount());
    }


    @Test
    @DisplayName("TableWithLabels - getColumnAt")
    void getColumnAt() {
        // assert that a column is correctly read
        assertEquals(List.of(0.0, 5.0, 10.0, 15.0, 20.0, 25.0), tableWithLabels.getColumnAt(0));
        // assert that a column contains the right amount of rows
        assertEquals(amountOfRows, tableWithLabels.getColumnAt(0).size());
    }

    @Test
    @DisplayName("TableWithLabels - getLabelAsInteger")
    public void getLabelAsInteger() {

        Integer class1 = tableWithLabels.getLabelAsInteger(tableWithLabels.getRowAt(0).getLabel());
        Integer class2 = tableWithLabels.getLabelAsInteger(tableWithLabels.getRowAt(1).getLabel());
        Integer class3 = tableWithLabels.getLabelAsInteger(tableWithLabels.getRowAt(3).getLabel());

        assertNotEquals(class1, class2);
        assertEquals(class1, class3);
    }

    private List<RowWithLabel> generateData(int nRows, int nColumns) {
        List<RowWithLabel> rows = new ArrayList<>();
        int counter = 0;
        // generate a table with consecutive double values
        for (int i = 0; i < nRows ; i++) {
            List<Double> numberList = new ArrayList<>();
            for (int j = i; j < i + nColumns; j++) {
                numberList.add(Double.valueOf(counter++));
            }
            rows.add(new RowWithLabel(numberList, labels.get(i % labels.size())));
        }
        return rows;
    }
}