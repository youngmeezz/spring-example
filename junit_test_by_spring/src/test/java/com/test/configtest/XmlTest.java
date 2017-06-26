package com.test.configtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlTest {	
	public static boolean TEST1;
	public static boolean TEST2;
	
	@BeforeClass
	public static void setUp() {
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
	
	@Test
	public void readXmlTest() throws Exception {
		File fXmlFile = new File("src/main/resources/staff.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		assertThat(doc.getDocumentElement().getNodeName(),is("company"));
		
		NodeList nodeList = doc.getElementsByTagName("staff");
		assertThat(nodeList.getLength(), is(2));
		
		for(int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			assertThat(node.getNodeName(), is("staff"));
			Element elt = (Element)node;
			System.out.println("First name : " + getTagValue("firstname",elt));
			System.out.println("Last name : " + getTagValue("lastname",elt));
			System.out.println("age : " + getTagValue("age",elt));			
		}
	}
	
	private String getTagValue(String tag, Element elt) {
		NodeList nodeList = elt.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node)nodeList.item(0);
		return node.getNodeValue();
	}
	

}
















