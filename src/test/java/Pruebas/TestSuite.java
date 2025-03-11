package Pruebas;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.scastele.machinelearning", "es.uji.scastele.csv", "es.uji.scastele.table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
