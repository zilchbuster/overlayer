package com.zilchbuster.overlayer;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class ImageFilePaths {
	private String outputRoot;
	private String inputRoot;
	
	public ImageFilePaths() throws IOException {
		this.setOutputRoot();
		this.createOutputRoot();
		
		this.setInputroot();
		this.createInputRoot();
	}

	private void setInputroot() throws IOException {
		this.inputRoot = FileUtils.getTempDirectoryPath() + "/overlayerInput";
	}
	
	private void createInputRoot() throws IOException {
		File outputRoot = new File(this.inputRoot);
		if (!outputRoot.exists()) {
			FileUtils.forceMkdir(outputRoot);
		}
	}
	
	private void setOutputRoot() throws IOException {
		this.outputRoot = FileUtils.getTempDirectoryPath() + "/overlayerOutput";
	}
	
	private void createOutputRoot() throws IOException {
		File outputRoot = new File(this.outputRoot);
		if (!outputRoot.exists()) {
			FileUtils.forceMkdir(outputRoot);
		}
	}
	
	public String getOutputPath(String uuid) {
		return this.outputRoot + "/output" + uuid + ".jpg";
	}
	
	public String[] getTwoInputPaths(String uuid) {
		String path1 = this.inputRoot + "/input1" + uuid + ".jpg";
		String path2 = this.inputRoot + "/input2" + uuid + ".jpg";
		String ret[] = { path1, path2 };
		return ret;
	}
}
