package Pruebas;// TODO: Remplazar <nombre> por el nombre de tu paquete

// TODO: Pon los imports especificos a tu proyecto

import es.uji.al435138.lectura.machinelearning.Distance;
import es.uji.al435138.lectura.machinelearning.ManhattanDistance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

class ManhattanDistanceTest {

    private Distance distance;
    private double delta = 0.00001;

    @BeforeEach
    void setUp() {
        distance = new ManhattanDistance();
    }

    @AfterEach
    void tearDown() {
        distance = null;
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(List.of(1.0, 1.0, 1.0, 1.0), List.of(1.0, 1.0, 1.0, 1.0), 0.0),
                Arguments.of(List.of(1.0, 1.0, 1.0, 1.0), List.of(1.0, 0.0, 1.0, 0.0), 2.0),
                Arguments.of(List.of(1.0, 1.5, 1.5, 1.0), List.of(3.3, 2.0, 3.3, 2.0), 5.6),
                Arguments.of(List.of(1.0, -3.0, 1.0, -3.0), List.of(-2.0, 5.0, -2.0, 5.0), 22.0),
                Arguments.of(List.of(0.0), List.of(-1.0), 1.0),
                Arguments.of(List.of(0.0, 0.0), List.of(1.0, 1.0), 2.0),
                Arguments.of(List.of(-1.5, -1.2, -1.7), List.of(0.0, 0.0, 0.0), 4.4),
                Arguments.of(List.of(0.0, 0.0, 0.0, 0.0), List.of(-1.0, -1.0, -1.0, -1.0), 4.0),
                Arguments.of(List.of(0.0, 0.0, 0.0, 0.0), List.of(0.0, 0.0, 0.0, 0.0), 0.0),
                Arguments.of(List.of(), List.of(), 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void calculateDistance(List<Double> p, List<Double> q, double expected) {
        assertEquals(expected, distance.calculateDistance(p, q), delta);
    }
}