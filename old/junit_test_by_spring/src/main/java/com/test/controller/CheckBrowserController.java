package com.test.controller;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CheckBrowserController {
	@RequestMapping(value="/testform",method=RequestMethod.GET)
	public String get(HttpServletRequest request, HttpServletResponse response) {
		return "form";
	}
	
	@RequestMapping(value="/testPost",method=RequestMethod.POST)
	public void post(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/octect-stream");
		ClassPathResource resource = new ClassPathResource("notice.jsp");
		try(InputStream is = resource.getInputStream();
					ServletOutputStream sos = response.getOutputStream()) {
			byte[] readDatas = new byte[100];
			int readLen = -1;
			String readLine = "";
			while( (readLen = is.read(readDatas)) != -1 ) {
				//sos.write(readDatas,0,readLen);
				readLine += new String(readDatas,0,readLen);
			}
			// System.out.println(readLine);
			//sos.flush();			
			//request.getRequestDispatcher("/notice").forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/notice")
	public void notice() {
		
	}
}
