package com.test.json;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties({"ignore1","ignore2"})
class SimpleDomain {
	String name;
	int age;
	String ignore1;
	String ignore2;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getIgnore1() {
		return ignore1;
	}
	public void setIgnore1(String ignore1) {
		this.ignore1 = ignore1;
	}
	public String getIgnore2() {
		return ignore2;
	}
	public void setIgnore2(String ignore2) {
		this.ignore2 = ignore2;
	}	
}

public class JacksonDatabindTest {	
	private SimpleDomain domain;
	//final String fileName = "D:\\test.json";	
	final String fileName = "src/test/resources/test.json";
	@Before
	public void setUp() {
		domain = new SimpleDomain();
		domain.setName("Simple Name");
		domain.setAge(11);
		domain.setIgnore1("Ignore 1");
		domain.setIgnore2("Ignore 2");
		
		File file = new File(fileName);
		if(file.exists()) {
			file.delete();
		}		
	}
	
	@Test
	public void mapper() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			// write file
			mapper.writeValue(new File(fileName),domain);
			
			// convert string and read
			String jsonInString = mapper.writeValueAsString(domain);
			// read from file
			String readString = null;
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				readString = br.readLine();			
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			assertThat(jsonInString,is(readString));
			
			// read from string
			String jsonString = "{\"name\":\"Simple Name\",\"age\":11}";
			SimpleDomain readDomain = mapper.readValue(jsonString, SimpleDomain.class);
			
			assertThat(domain.getName(),is(readDomain.getName()));
			assertThat(domain.getAge(),is(readDomain.getAge()));
			assertNull(readDomain.getIgnore1());
			assertNull(readDomain.getIgnore2());
						
			// pretty printer
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(domain));			
		}
		catch(JsonGenerationException e) {
			e.printStackTrace();
		}
		catch(JsonMappingException e) {
			e.printStackTrace();			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}












