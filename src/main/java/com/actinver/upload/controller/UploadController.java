package com.actinver.upload.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.actinver.upload.service.FileService;

@Controller
public class UploadController {

	private static final Logger logger = Logger.getLogger(UploadController.class);

	@Autowired
	private FileService unzipService;

	@RequestMapping("/")
	public String inicio() {
		return "index";
	}

	@RequestMapping(value = "/uploadSingle", method = RequestMethod.POST)
	public String uploadSingleFile(HttpServletRequest request, @RequestParam("file1") MultipartFile fileUpload) {

		String result = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd hh_mm_ss");
		String route = createDirectory(request).toString();

		if (logger.isDebugEnabled()) {
			logger.debug(route);
			logger.debug(fileUpload.getOriginalFilename());
			logger.debug(fileUpload.getContentType());
			logger.debug(fileUpload.getSize());
		}

		if (fileUpload.getOriginalFilename().contains(".zip")) {
			File file = new File(
					route + File.separator + dateFormat.format(new Date()) + "_" + fileUpload.getOriginalFilename());
			try {
				fileUpload.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean unZipFiles = unzipService.unZipFiles(file, route + "//");
			if(unZipFiles){
				List<File> fileListOfXML = unzipService.getFileListOfXML(route + "//");
				
				
			}

			result = "success";

		} else {
			result = "failed";
		}

		return result;
	}

	@RequestMapping(value = "/uploadMultiple", method = RequestMethod.POST)
	private String uploadMultipleFile(HttpServletRequest request, @RequestParam("files") MultipartFile[] filesUpload) {

		logger.info(filesUpload.length);
		if (filesUpload.length != 0) {
			for (MultipartFile file : filesUpload) {
				logger.info(file.getOriginalFilename());
				logger.info(file.getContentType());
				logger.info(file.getSize());
			}
		}

		return "success";
	}

	private File createDirectory(HttpServletRequest request) {
		File file = new File("C:\\Directory1");
		if (!file.exists()) {
			if (file.mkdir()) {
				logger.info("Directory is created!");
			} else {
				logger.info("Failed to create directory!");
			}
		}

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

}
