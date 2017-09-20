package com.zilchbuster.overlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;

import com.zilchbuster.overlayer.Overlayer;
import com.zilchbuster.overlayer.Image;
import com.zilchbuster.overlayer.ImageRepository;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * TODO: change this so that they're 2 endpoints,
 * one of them takes 2 files and returns a token on success (using uuid,
 * code up an interface and simply implement it with uuid generation)
 * endpoint also generates overlayed image
 * second endpoint accepts the valid token and returns the image
 * Save generated token, and use token to generate image input and output storage paths.
 */

@Controller
@RequestMapping(path="/overlay", produces="application/json") 
public class OverlayerController {
	@Autowired 
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageFilePaths imageFilePaths;
	
	@Autowired
	private Overlayer imageOverlayer;

	@PostMapping(path="/overlay", produces="application/json")
	public @ResponseBody String overlay(@RequestParam MultipartFile imageBlob1, @RequestParam MultipartFile imageBlob2, @RequestParam double opacity) {
		Image image = new Image();
		String imageToken = image.getToken();
		String paths[] = this.imageFilePaths.getTwoInputPaths(imageToken);
		String outputPath = this.imageFilePaths.getOutputPath(imageToken);
		imageRepository.save(image);
		File file1 = new File(paths[0]); 
		File file2 = new File(paths[1]); 
		
		try {
			imageBlob1.transferTo(file1);
			imageBlob2.transferTo(file2);
		} catch (IllegalStateException | IOException e) {
			return "{\"status\":\"fail\", \"message\": \"couldn't save input files\"}";
		}
		
		try {
			this.imageOverlayer.prepImages(paths[0], paths[1]);
			this.imageOverlayer.overlayOnce(opacity, outputPath);
		} catch (IOException e1) {
			return "{\"status\":\"fail\", \"message\": \"couldn't  overlay\"}";
		}
		
		return "{\"status\":\"success\",\"token\":\"" + imageToken +"\"}";
	}

	@GetMapping(path="/image")
	public @ResponseBody byte[] image(@RequestParam String token){
		Image image = this.imageRepository.findOneByToken(token);
		
		if(image==null) {
			return "Error: no such token".getBytes();
		}
		
		byte[] responseContent;
		String imageToken = image.getToken();
		String outputPath = this.imageFilePaths.getOutputPath(imageToken);
		try {
			responseContent = Files.readAllBytes(Paths.get(outputPath));
		} catch (IOException e) {
			return "Error: couldn't read overlayed output".getBytes();
		}
		
		return responseContent;
	}
}