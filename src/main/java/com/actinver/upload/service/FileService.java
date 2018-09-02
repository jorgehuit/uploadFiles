package com.actinver.upload.service;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileService {

	public boolean unZipFiles(File zipfile, String descDir);
	public List<File> getFileListOfXML(String path);
	public Map<String, String> getAttributes(List<File> fileListOfXML);
}
