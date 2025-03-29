package Pruebas;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.al435138.lectura.machinelearning", "es.uji.al435138.lectura.csv", "es.uji.al435138.lectura.table", "es.uji.al435138.lectura.recommender"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
