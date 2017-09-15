package com.zilchbuster.overlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;

import com.zilchbuster.overlayer.Image;
import com.zilchbuster.overlayer.ImageRepository;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/overlay", produces="application/json") 
public class OverlayerController {
	@Autowired 
	private ImageRepository imageRepository;

	@PostMapping(path="/overlay", produces="application/json")
	public @ResponseBody String overlay (@RequestParam MultipartFile imageBlob1, @RequestParam MultipartFile imageBlob2, @RequestParam String token) {
		String tempDir = System.getProperty("java.io.tmpdir");
		String imagePath1 = tempDir + "/1_" + token;
		String imagePath2 = tempDir + "/2_" + token;
		String outputPath = tempDir + "/out_" + token + ".jpg";
		File file1 = new File(imagePath1); 
		File file2 = new File(imagePath2); 
		imageRepository.save(new Image(imagePath1, imagePath2, token));
		
		try {
			imageBlob1.transferTo(file1);
			imageBlob2.transferTo(file2);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "Error: couldn't save input file 2";
		}

		ProcessBuilder pb = new ProcessBuilder(
				"gimp", 
				"-i", 
				"-b", 
				"'(overlay-images \"" + imagePath1 + 
				"\" \"" + imagePath2 + "\" \"" + outputPath + "\" 50)'",
				"-b",
				"'(gimp-quit 0)'"
		);
		try {
			pb.directory(new File(tempDir));
			pb.redirectErrorStream(true);
			Process p = pb.start();
			InputStream inputStream = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			
			int count = 0;
			while ((line = reader.readLine()) != null) {
			    if(line.contains("batch command executed successfully")) {
			    	count++;
			    	System.out.println(line);
			    	if(count>1) {
						inputStream.close();
						p.getErrorStream().close();
						p.getOutputStream().close();
						p.destroy();
						break;
			    	}
			    }
			}
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "Error: couldn't run overlay command";
		} 
		
		String content;
		try {
			content = new String(Files.readAllBytes(Paths.get(outputPath)));
		} catch (IOException e) {
			e.printStackTrace();
			return "Error: couldn't read overlayed output";
		}
		
		return content;
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Image> getAllUsers() {
		return imageRepository.findAll();
	}
}