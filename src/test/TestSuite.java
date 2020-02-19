package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	Validierungstests.class,
	Fehlertests.class,
	MyClassTest.class
})

public class TestSuite { }