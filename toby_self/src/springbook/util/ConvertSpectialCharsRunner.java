package springbook.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.junit.Test;

public class ConvertSpectialCharsRunner {	
	@Test
	public void test() {		
		try( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")))) {			
			while(true) {
				String str = reader.readLine();
				if(str == null) break;
				writer.write(ConvertHtmlSpecialChar.convertHTML(str));
				writer.write("\r\n");
			}				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
