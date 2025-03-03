package RunPruebas;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class Ejecutable {

	public static void main(String[] args) {

		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { RunPruebas.RunPrincipalTest.class });
		testng.addListener(tla);
		testng.run();

		
	}

}
