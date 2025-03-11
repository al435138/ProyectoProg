// TODO: Reemplazar por el nombre de tu paquete
package Pruebas;

// TODO: Reemplazar por los imports de tu proyecto
import es.uji.scastele.csv.CSV;
import es.uji.scastele.table.TableWithLabels;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class KNNTest {

    private KNN knn;

    @BeforeEach
    void setUp() throws IOException {
        TableWithLabels iris = new CSV().readTableWithLabels("iris.csv");
        knn = new KNN();
        knn.train(iris);
    }

    @AfterEach
    void tearDown() {
        knn = null;
    }

    static Stream<Arguments> estimateData() {
        return Stream.of(
                // existing samples in the dataset
                Arguments.of(List.of(5.1,3.5,1.4,0.2), Integer.valueOf(0)),
                Arguments.of(List.of(7.0,3.2,4.7,1.4), Integer.valueOf(1)),
                Arguments.of(List.of(6.3,3.3,6.0,2.5), Integer.valueOf(2)),
                // new samples, very close to an existing sample
                Arguments.of(List.of(5.1,3.1,1.0,0.3), Integer.valueOf(0)),
                Arguments.of(List.of(6.1,3.3,4.4,1.7), Integer.valueOf(1)),
                Arguments.of(List.of(7.8,3.1,6.1,2.2), Integer.valueOf(2))
        );
    }

    @ParameterizedTest
    @MethodSource("estimateData")
    @DisplayName("KNN - estimate")
    void estimate(List<Double> sample, Integer result) {
        // assert that points within (first 3) and without (last 3) the training dataset estimate to the expected class
        assertEquals(result, knn.estimate(sample));
    }
}