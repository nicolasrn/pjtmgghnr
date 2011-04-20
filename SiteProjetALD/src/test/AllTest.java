package test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTest.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestCategorie.class);
		suite.addTestSuite(TestDroit.class);
		suite.addTestSuite(TestMotCle.class);
		suite.addTestSuite(TestDepartementRegion.class);
		suite.addTestSuite(TestInternaute.class);
		suite.addTestSuite(TestEncherit.class);
		//$JUnit-END$
		return suite;
	}

}
