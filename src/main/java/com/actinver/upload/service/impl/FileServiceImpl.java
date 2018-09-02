package com.actinver.upload.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.actinver.upload.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

	public boolean unZipFiles(File zipfile, String descDir) {
		boolean unzip = false;
		try {
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				OutputStream out = new FileOutputStream(descDir + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
				logger.info("Descompresión completa.");
			}
			zf.close();
			unzip = true;

		} catch (Exception e) {
			logger.error("Error al descomprimir el archivo ZIP + " + e.getMessage());
			e.printStackTrace();
		}

		return unzip;
	}

	public List<File> getFileListOfXML(String path) {

		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<File> listOfXML = new ArrayList<File>();

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".xml")) {
				files = listOfFiles[i].getName();
				listOfXML.add(listOfFiles[i]);
				logger.info(files);
			}
		}

		return listOfXML;
	}

	public Map<String, String> getAttributes(List<File> fileListOfXML) {

		for (File file : fileListOfXML) {
			
		}
		
		return null;
	}
}
