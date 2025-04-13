package Pruebas;// TODO: Remplazar <nombre> por el nombre de tu paquete

// TODO: Pon los imports especificos a tu proyecto


import es.uji.al435138.machinelearning.Distance;
import es.uji.al435138.machinelearning.EuclideanDistance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EuclideanDistanceTest {

    private Distance distance;
    private double delta = 0.00001;

    @BeforeEach
    void setUp() {
        distance = new EuclideanDistance();
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(List.of(1.0, 1.0, 1.0, 1.0), List.of(1.0, 1.0, 1.0, 1.0), 0.0),
                Arguments.of(List.of(1.0, 1.0, 1.0, 1.0), List.of(1.0, 0.0, 1.0, 0.0), 1.414214),
                Arguments.of(List.of(1.0, 1.5, 1.5, 1.0), List.of(3.3, 2.0, 3.3, 2.0), 3.1273),
                Arguments.of(List.of(1.0, -3.0, 1.0, -3.0), List.of(-2.0, 5.0, -2.0, 5.0), 12.08305),
                Arguments.of(List.of(0.0), List.of(-1.0), 1.0),
                Arguments.of(List.of(0.0, 0.0), List.of(1.0, 1.0), 1.414214),
                Arguments.of(List.of(-1.0, -1.0, -1.0), List.of(0.0, 0.0, 0.0), 1.73205),
                Arguments.of(List.of(0.0, 0.0, 0.0, 0.0), List.of(-1.0, -1.0, -1.0, -1.0), 2.0),
                Arguments.of(List.of(0.0, 0.0, 0.0, 0.0), List.of(0.0, 0.0, 0.0, 0.0), 0.0),
                Arguments.of(List.of(), List.of(), 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void calculateDistance(List<Double> p, List<Double> q, double expected) {
        assertEquals(expected, distance.calculateDistance(p, q), delta);
    }

    @AfterEach
    void tearDown() {
        distance = null;
    }
}