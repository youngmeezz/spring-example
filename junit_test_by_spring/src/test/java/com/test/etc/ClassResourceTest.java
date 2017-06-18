package com.test.etc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class ClassResourceTest {
	private static final Logger logger = LoggerFactory.getLogger(ClassResourceTest.class);
		
	@Test
	public void classPathResourceTest() throws Exception {
		// project\target\classes\test.txt 
		ClassPathResource resource = new ClassPathResource("test.txt");
		
		//check file name
		Assert.assertThat( resource.getFilename(), is("test.txt"));
		logger.info( resource.getFile().getAbsolutePath() );		
		try( Scanner sc = new Scanner(resource.getInputStream()) ) {
			String lineString = sc.nextLine();
			assertThat(lineString,is("class resource test"));
		} catch(IOException e) {
			e.printStackTrace();
		}			
	}
}
