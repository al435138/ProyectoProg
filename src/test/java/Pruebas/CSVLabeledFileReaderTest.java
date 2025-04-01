package Pruebas;// TODO: Remplazar <nombre> por el nombre de tu paquete

<nombre>;

// TODO: Pon los imports especificos a tu proyecto

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVLabeledFileReaderTest {

    private CSVLabeledFileReader reader;

    @BeforeEach
    void setUp() {
        reader = new CSVLabeledFileReader("iris.csv");
    }

    @AfterEach
    void tearDown() {
        reader = null;
    }

    // TODO: Dependiendo de cómo manejas las excepciones, puedes añadir un try/catch o un lanzamiento de excepción aquí.    
    @Test
    void readTableFromSource() {
        TableWithLabels table = (TableWithLabels) reader.readTableFromSource();
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