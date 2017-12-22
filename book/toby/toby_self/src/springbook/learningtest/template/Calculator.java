package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	public Integer calcSum(String filepath) throws IOException {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {				
				return value + Integer.valueOf(line);
			}			
		};
		return lineReadTemplate(filepath,sumCallback,0);
	}
	
	public Integer calcMultiply(String filepath) throws IOException {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {				
				return value * Integer.valueOf(line);
			}			
		};
		return lineReadTemplate(filepath,sumCallback,1);
	}
	
	public String concatenate(String filepath) throws IOException {
		LineCallback<String> callback = new LineCallback<String>() {
			@Override
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}			
		};
		return lineReadTemplate(filepath,callback,"");
	}
	
	public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			T res = initVal;
			String line = null;
			while( (line = br.readLine()) != null ) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if( br != null ) { try{ br.close();}
			catch(IOException e){System.out.println(e.getMessage());} }
		}
	}
	
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			// 콜백 오브젝트 호출. 템플릿에서 만든 컨텍스트 정보인 BufferedReader를 전달해 주고
			// 콜백의 작업 결과를 받아 둔다.
			int ret = callback.doSomethingWithReader(br); 
			return ret;
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if( br != null ) { try{ br.close();}
			catch(IOException e){System.out.println(e.getMessage());} }
		}
	}
}
