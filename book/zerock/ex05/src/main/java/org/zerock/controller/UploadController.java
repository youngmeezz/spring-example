package org.zerock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.util.MediaUtils;
import org.zerock.util.UploadFileUtils;

@Controller
public class UploadController {
	// 설정된 경로를 이용
	@Resource(name = "uploadPath")
	private String uploadPath;
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	///////////////////
	// ajax이용 파일 업로드
	//////////////////
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {
		// empty
	}
	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	// produces : 한국어로 전송하기 위해
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("original name : " + file.getOriginalFilename());
		// logger.info("size : "+file.getSize());
		// logger.info("contentType : "+file.getContentType());
		// return new
		// ResponseEntity<>(file.getOriginalFilename(),HttpStatus.CREATED);
		// HttpStatus.CREATE : 원하는 리소스가 정상적으로 생성되었는 상태코드 (HttpStatus.OK를 이용해도 무방)		
		return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()),
				HttpStatus.CREATED);
	}

	///////////////////
	// 파일 전송 기능 구현
	// param : 전송받기 원하는 파일 이름
	// return : 파일의 데이터
	// (@ResponseBody 를 이용해서 byte[] 데이터가 그대로 전송)
	//////////////////	
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		logger.info("FILE NAME : " + fileName);
		
		try {
			//파일 확장자 check
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);

			//MIME 타입 설정하기 위해 
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(uploadPath + fileName);

			if (mType != null) { // 이미지 이면
				headers.setContentType(mType); //MediaType.IMAGE_XXXX
			} else { // 이미지가 아니면 다운로드 받기 가능
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				//MIME타입 : 다운로드 용 'application/octet-stream'
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				//한글 인코딩
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}
			//IOUtils.toByteArray(in); 실제 데이터 읽음
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	///////////////////
	// 파일 삭제 처리
	// @param : 삭제할 파일 이름
	// 이미지 파일일 경우 원본 삭제 & 썸네일 삭제
	//////////////////
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {
		logger.info("delete file : " + fileName);

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		MediaType mType = MediaUtils.getMediaType(formatName);

		if (mType != null) { // 이미지 이면 원본 삭제
			String front = fileName.substring(0, 12); // '/년/월/일'
			String end = fileName.substring(14); // 's_'를 뺀 파일 이름
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete(); // 원본 삭제																							
		}		
		// 일반파일|썸네일파일 삭제
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete(); 
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}

	///////////////////
	// 삭제 처리
	//////////////////
	@ResponseBody
	@RequestMapping(value = "/deleteAllFiles", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(@RequestParam("files[]") String[] files) {
		logger.info("delete all files : " + files);

		if (files == null || files.length == 0) {
			return new ResponseEntity<String>("deleted", HttpStatus.OK);
		}

		for (String fileName : files) {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = MediaUtils.getMediaType(formatName);

			if (mType != null) {
				String front = fileName.substring(0, 12);
				String end = fileName.substring(14);
				new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
			}

			new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		}

		return new ResponseEntity<String>("deleted", HttpStatus.OK);

	}

	///////////////////
	// 일반 <form>태그
	//////////////////
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		// empty
	}

	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public String uploadForm(MultipartFile file, Model model) throws Exception {
		logger.info("originalName : " + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());

		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		model.addAttribute("savedName", savedName);

		return "uploadResult";
	}

	private String uploadFile(String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID(); // 중복되지 않는 고유한 키 값을 설정할 때 사용

		String savedName = uid.toString() + "_" + originalName;

		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(fileData, target); // 파일 카피

		return savedName;
	}

}
