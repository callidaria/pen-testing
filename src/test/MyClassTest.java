package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyClassTest {

	@BeforeEach
	void setUp() throws Exception {
		//nothing
		/* Ich hab bei validate die nicht genutzten Parameter entfernt und benutze nur den Konstruktor
		 */
	}

	@Test
	void test1() {
		int l,s,e;
		l=-1;
		s=0;
		e=0;
		MyClass myClass = new MyClass(l,s,e);
		assertEquals(l, myClass.l);
		assertEquals(s, myClass.s);
		assertEquals(e, myClass.e);
		myClass.validate();
		assertEquals(-1, myClass.l);
		assertEquals(0, myClass.s);
		assertEquals(0, myClass.e);
	}
	@Test
	void test2() {
		int l,s,e;
		l=10;
		s=400;
		e=100;
		MyClass myClass = new MyClass(l,s,e);
		assertEquals(l, myClass.l);
		assertEquals(s, myClass.s);
		assertEquals(e, myClass.e);
		myClass.validate();
		assertEquals(0, myClass.l);
		assertEquals(400, myClass.s);
		assertEquals(100, myClass.e);
	}
	@Test
	void test3() {
		int l,s,e;
		l=10;
		s=300;
		e=30;
		MyClass myClass = new MyClass(l,s,e);
		assertEquals(l, myClass.l);
		assertEquals(s, myClass.s);
		assertEquals(e, myClass.e);
		myClass.validate();
		assertEquals(0, myClass.l);
		assertEquals(390, myClass.s);
		assertEquals(30, myClass.e);
	}
	@Test
	void test4() {
		int l,s,e;
		l=10;
		s=300;
		e=60;
		MyClass myClass = new MyClass(l,s,e);
		assertEquals(l, myClass.l);
		assertEquals(s, myClass.s);
		assertEquals(e, myClass.e);
		myClass.validate();
		assertEquals(0, myClass.l);
		assertEquals(300, myClass.s);
		assertEquals(60, myClass.e);
	}
	

}
