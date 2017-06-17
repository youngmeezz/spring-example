package com.test.etc;

import static org.hamcrest.CoreMatchers.is;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class ClassResourceTest {
	
	@Test
	public void classPathResourcePreTest() throws Exception {
		ClassPathResource resource = new ClassPathResource("./table.sql");
		Assert.assertTrue(resource.exists());
	}
		
	//@Test
	public void classPathResourceTest() throws Exception {
		ClassPathResource resource = new ClassPathResource("file:resources/test.txt");
		
		//check file name
		Assert.assertThat( resource.getFilename(), is("test.txt"));
		//System.out.println( resource.getFile().getAbsolutePath() );
		InputStream is = null;
		try {			
			File file = File.createTempFile("test",".txt");
			FileCopyUtils.copy(resource.getInputStream(), new FileOutputStream(file) );
			is = new BufferedInputStream( new FileInputStream( file ) );
			byte[] readData = new byte[100];
			int readLen = -1;
			String txt = "";
			while( (readLen = is.read(readData)) != -1 ) {
				txt += new String(readData);
			}
			Assert.assertThat( txt , is("class resource test"));			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if( is != null )
				try {is.close();}catch(Exception e){}
		}		
	}
}
