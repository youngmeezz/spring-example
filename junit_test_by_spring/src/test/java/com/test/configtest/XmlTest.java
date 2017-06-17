package com.test.configtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlTest {	
	public static boolean TEST1;
	public static boolean TEST2;
	static {
		File file = new File("src/main/resources/test_config.xml");
		try {
			setConfigLocation( new FileSystemResource(file));			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initTest() {
		assertTrue(TEST1);
		assertThat(TEST2, is(false) );
	}
		
	private static void setConfigLocation(Resource configLocation) throws ParserConfigurationException, SAXException, IOException, DOMException, XPathExpressionException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();        
        initializeConfig(builder.parse(configLocation.getFile()));		
	}
	
	private static void initializeConfig(Document document) throws DOMException, XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();		
		TEST1 = Boolean.parseBoolean(((Node) xpath.evaluate("general/function/test1",  document, XPathConstants.NODE)).getTextContent().trim()); 
		TEST2 = Boolean.parseBoolean(((Node) xpath.evaluate("general/function/test2",  document, XPathConstants.NODE)).getTextContent().trim());		
	}
	

}
