package messageControl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MessageTest {
	
	Message m1;
	ChatControl cc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cc = new ChatControl();
		m1 = new Message(null, "Guten Abend", "18:21:10", "2015-12-07", cc.id.getMessageId());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test (expected=NullPointerException.class)
	public void testGetName() {
		assertEquals("Niels", m1.getName());
	}

	@Test
	public void testSetName() {
		m1.setName("Peter");
		assertEquals("Peter", m1.getName());
	}
	
	@Test
	public void testGetTime() {
		assertEquals("18:21:10", m1.getTime());
	}

	@Test
	public void testSetTime() {
		m1.setTime("19:21:10");
		assertEquals("19:21:10", m1.getTime());
	}
	
	@Test
	public void testGetDate() {
		assertEquals("2015-12-07", m1.getDate());
	}

	@Test
	public void testSetDate() {
		m1.setDate("2015-12-08");
		assertEquals("2015-12-08", m1.getDate());
	}

	@Test
	public void testGetMessage() {
		assertEquals("Guten Abend", m1.getMessage());
	}

	@Test
	public void testSetMessage() {
		m1.setMessage("Guten Morgen");
		assertEquals("Guten Morgen", m1.getMessage());
	}
	
	@Test
	public void testGetId() {
		assertEquals(0, m1.getId());
	}

	@Test
	public void testSetId() {
		m1.setId(10);
		assertEquals(10, m1.getId());
	}
}
