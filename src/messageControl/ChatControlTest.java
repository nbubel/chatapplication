package messageControl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.io.File;

import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test die Hauptfunktionen der ChatControl-Klasse
 * werden Nachrichten, die vom Client kommen richtig geparst 
 * als Messageobjekt gespeichert und anschliessend wieder 
 * ausgegeben?
 * 
 * @author Niels Bubel
 *
 */
public class ChatControlTest extends Mockito {

	ChatControl cc;
	Message m1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cc = new ChatControl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoPostHttpServletRequestHttpServletResponse() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		String mockString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<messages xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation="
				+ "'http://localhost:8080/CambiChat/schema.xsd'>"
				+ "<message><id>0</id><date>2015-12-07</date><time>18:21:10</time><name>Niels</name>"
				+ "<nachricht>Guten Abend</nachricht><group>Bubel</group>"
				+ "</message></messages>";

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				mockString.getBytes(StandardCharsets.UTF_8));
		ServletInputStream servletInputStream = new ServletInputStream() {
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};

		when(request.getInputStream()).thenReturn(servletInputStream);
		PrintWriter writer = new PrintWriter("test.txt");
		when(response.getWriter()).thenReturn(writer);
		when(request.getParameter("id")).thenReturn("0");
		when(request.getParameter("group")).thenReturn("Bubel");

		cc.doPost(request, response);
		cc.doGet(request, response);

		writer.flush();
		File file = new File("test.txt");
		String str = FileUtils.readFileToString(file);
		assertTrue(str.contains(
				"<message><id>1</id><date>2015-12-07</date><time>18:21:10</time><name>Niels</name>"
				+ "<nachricht>Guten Abend</nachricht><group>Bubel</group></message>"));
	}

}
