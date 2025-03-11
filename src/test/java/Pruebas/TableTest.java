// TODO: Reemplazar por el nombre de tu paquete
package Pruebas;

// TODO: Reemplazar por los imports de tu proyecto
import es.uji.scastele.table.Row;
import es.uji.scastele.table.Table;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    private Table table;
    private int amountOfColumns;
    private List<String> headers;
    private int amountOfRows =  10;

    public TableTest() {
        this.headers = List.of("header1", "header2", "header3", "header4");
        this.amountOfColumns = headers.size();
    }

    @BeforeEach
    void setUp() throws IOException {
        List<Row> rows = generateData(amountOfRows, amountOfColumns);

        // Hay dos opciones para crear las tablas:
        // (1) Añadiendo la cabecera y luego fila a fila con funciones para ello, como "setHeaders" y  "addRow".
        // (2) Inyectando la cabecera y las filas mediante el constructor.
        // TODO: Descomenta/comenta la opcion que uses/no uses.

        //---(1) Setter y addRow---
        table = new Table();
        table.setHeaders(headers);
        rows.forEach(row -> table.addRow(row));
        //-------------------------

        //---(2) Inyeccion por constructor---
        //table = new Table(headers, rows);
        //-----------------------------------
    }

    @AfterEach
    void tearDown() {
        table = null;
    }

    @Test
    @DisplayName("Table - getHeaders")
    void getHeaders() {
        // assert if the headers are correctly read
        assertEquals(List.of("header1", "header2", "header3", "header4"), table.getHeaders());
    }

    @Test
    @DisplayName("Table - getRowAt")
    void getRowAt() {
        // assert that the first and last row are correctly read
        assertEquals(List.of(0.0, 1.0, 2.0, 3.0), table.getRowAt(0).getData());
        assertEquals(List.of(36.0, 37.0, 38.0, 39.0), table.getRowAt(this.amountOfRows - 1).getData());
        // assert that an arbitrary row is correctly read
        assertEquals(List.of(12.0, 13.0, 14.0, 15.0), table.getRowAt(3).getData());
        // assert that the right amount of columns is read
        assertEquals(this.amountOfColumns, table.getRowAt(3).getData().size());
    }

    @Test
    @DisplayName("Table - rowCount")
    void rowCount() {
        assertEquals(amountOfRows, table.getRowCount());
    }

    @Test
    @DisplayName("Table - getColumnAt")
    void getColumnAt() {
        // assert that a column is correctly read
        assertEquals(List.of(0.0, 4.0, 8.0, 12.0, 16.0, 20.0, 24.0, 28.0, 32.0, 36.0), table.getColumnAt(0));
        // assert that a column contains the right amount of rows
        assertEquals(amountOfRows, table.getColumnAt(0).size());
    }

    private List<Row> generateData(int nRows, int nColumns) {
        List<Row> rows = new ArrayList<>();
        int counter = 0;
        // generate a table with consecutive double values
        for (int i = 0; i < nRows ; i++) {
            List<Double> numberList = new ArrayList<>();
            for (int j = i; j < i + nColumns; j++) {
                numberList.add(Double.valueOf(counter++));
            }
            rows.add(new Row(numberList));
        }
        return rows;
    }

}