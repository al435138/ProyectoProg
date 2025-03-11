package Pruebas;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;



@Suite
@SuiteDisplayName("Lanzar todos los tests")
@SelectPackages({"es.uji.al435138.machinelearning", "es.uji.al435138.csv", "es.uji.al435138.table"})
@IncludeClassNamePatterns(".*Test")
public class TestSuite {
}
