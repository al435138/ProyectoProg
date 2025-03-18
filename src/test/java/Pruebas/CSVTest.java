// TODO: Reemplazar por el nombre de tu paquete
package Pruebas;

// TODO: Reemplazar por los imports de tu proyecto
import es.uji.al435138.lectura.csv.CSV;
import es.uji.al435138.lectura.table.Table;
import es.uji.al435138.lectura.table.TableWithLabels;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVTest {

    private CSV csv;
    private String milesFile = "miles_dollars.csv";
    private String irisFile = "iris.csv";

    @BeforeEach
    void setUp() {
        csv = new CSV();
    }

    @AfterEach
    void tearDown() {
        csv = null;
    }

    @Test
    @DisplayName("CSV - readTable")
// TODO: En caso de manejar la excepción en CSV, puedes eliminar la declaración "throws" aquí
    void readTable() throws IOException, URISyntaxException {
        Table table = csv.readTable(milesFile);

        // assert that the table is not null
        assertNotNull(table);
        // assert that the table object is actually an instance of Table
        assertInstanceOf(Table.class, table);

        // assert that the correct number of rows are read
        assertEquals(25, table.getRowCount());

        // assert that the headers are correctly read
        assertEquals(List.of("Miles", "Dollars"), table.getHeaders());

        // assert that the first and last row are correctly read
        assertEquals(List.of(1211.0,1802.0), table.getRowAt(0).getData());
        assertEquals(List.of(5439.0,6964.0), table.getRowAt(24).getData());
        // assert that an arbitrary row is correctly read
        assertEquals(List.of(3852.0,4801.0), table.getRowAt(16).getData());
    }

    @Test
    @DisplayName("CSV - readTableWithLabels")
// TODO: En caso de manejar la excepción en CSV, puedes eliminar la declaración "throws" aquí
    void readTableWithLabels() throws IOException, URISyntaxException {
        TableWithLabels table = csv.readTableWithLabels(irisFile);

        // assert that the table is not null
        assertNotNull(table);
        // assert that the table object is actually an instance of TableWithLabels
        assertInstanceOf(TableWithLabels.class, table);

        // assert that the correct number of rows are read
        assertEquals(150, table.getRowCount());

        // assert that the headers are correctly read
        assertEquals(List.of("sepal length","sepal width","petal length","petal width"), table.getHeaders());
        // assert that the first and last row are correctly read
        assertEquals(List.of(5.1,3.5,1.4,0.2), table.getRowAt(0).getData());
        assertEquals("Iris-setosa", table.getRowAt(0).getLabel());
        assertEquals(List.of(5.9,3.0,5.1,1.8), table.getRowAt(149).getData());
        assertEquals("Iris-virginica", table.getRowAt(149).getLabel());
        // assert that an arbitrary row is read correctly
        assertEquals(List.of(5.6,3.0,4.5,1.5), table.getRowAt(66).getData());
        assertEquals("Iris-versicolor", table.getRowAt(66).getLabel());
    }


}